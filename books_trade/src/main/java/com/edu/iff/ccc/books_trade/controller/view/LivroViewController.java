package com.edu.iff.ccc.books_trade.controller.view;

import com.edu.iff.ccc.books_trade.dto.LivroDTO;
import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.service.LivroService;
import com.edu.iff.ccc.books_trade.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/livros")
public class LivroViewController {

    private final LivroService livroService;
    private final UsuarioService usuarioService;

    public LivroViewController(LivroService livroService, UsuarioService usuarioService) {
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarLivros(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("outrosLivros", livroService.findAllLivros());
            return "livros";
        }
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Long meuId = usuarioLogado.getId();
        List<Livro> meusLivros = livroService.findLivrosByDonoId(meuId);
        List<Livro> outrosLivros = livroService.findLivrosDisponiveis(meuId);
        model.addAttribute("meusLivros", meusLivros);
        model.addAttribute("outrosLivros", outrosLivros);
        return "livros";
    }

    @GetMapping("/novo")
    public String novoLivroForm(Model model, Principal principal) {
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setDonoId(usuarioLogado.getId());
        model.addAttribute("livroDTO", livroDTO);
        return "livro_form";
    }

    @PostMapping
    public String salvarLivro(@Valid @ModelAttribute("livroDTO") LivroDTO livroDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "livro_form";
        }
        UsuarioComum donoComum = (UsuarioComum) usuarioService.findUsuarioById(livroDTO.getDonoId())
                .orElseThrow(() -> new IllegalArgumentException("Dono do livro inválido. ID: " + livroDTO.getDonoId()));
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setGenero(livroDTO.getGenero());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setEstadoConservacao(livroDTO.getEstadoConservacao());
        donoComum.addLivro(livro);
        
        // CORREÇÃO APLICADA AQUI:
        usuarioService.updateUsuario(donoComum); 
        
        return "redirect:/livros";
    }

    @GetMapping("/{id}")
    public String detalhesLivro(@PathVariable("id") Long id, Model model) {
        Livro livro = livroService.findLivroById(id);
        model.addAttribute("livro", livro);
        return "livro";
    }
}



