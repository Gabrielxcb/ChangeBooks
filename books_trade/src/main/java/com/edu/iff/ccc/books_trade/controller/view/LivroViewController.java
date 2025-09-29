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
import org.springframework.lang.Nullable;
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
    public String listarLivros(Model model, @Nullable Principal principal) {
        if (principal == null) {
            model.addAttribute("outrosLivros", livroService.findAllLivros());
            return "livros";
        }
        // MUDANÇA AQUI: Chamada direta, sem .orElseThrow()
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName());
    
        Long meuId = usuarioLogado.getId();
        List<Livro> meusLivros = livroService.findLivrosByDonoId(meuId);
        List<Livro> outrosLivros = livroService.findLivrosDisponiveis(meuId);
        model.addAttribute("meusLivros", meusLivros);
        model.addAttribute("outrosLivros", outrosLivros);
        return "livros";
    }

    @GetMapping("/novo")
    public String novoLivroForm(Model model, Principal principal) {
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName());
    
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setDonoId(usuarioLogado.getId());
        model.addAttribute("livroDTO", livroDTO);
        return "livro_form";
    }

    @PostMapping
    public String salvarOuAtualizarLivro(@Valid @ModelAttribute("livroDTO") LivroDTO livroDTO,
                                         BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "livro_form";
        }
        
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName());

        if (livroDTO.getId() == null) {
            UsuarioComum donoComum = (UsuarioComum) usuarioLogado;
            Livro novoLivro = new Livro();
            
            novoLivro.setTitulo(livroDTO.getTitulo());
            novoLivro.setAutor(livroDTO.getAutor());
            novoLivro.setGenero(livroDTO.getGenero());
            novoLivro.setDescricao(livroDTO.getDescricao());
            novoLivro.setAnoPublicacao(livroDTO.getAnoPublicacao());
            novoLivro.setEstadoConservacao(livroDTO.getEstadoConservacao());
            
            donoComum.addLivro(novoLivro);
            usuarioService.updateUsuario(donoComum);
        } else {
            livroService.updateLivro(livroDTO.getId(), livroDTO, usuarioLogado);
        }
        
        return "redirect:/livros";
    }

    @GetMapping("/{id}/editar")
    public String editarLivroForm(@PathVariable("id") Long livroId, Model model, Principal principal) {
        Livro livro = livroService.findLivroById(livroId);
        
        if (!livro.getDono().getEmail().equals(principal.getName())) {
            return "redirect:/livros"; 
        }

        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(livro.getId());
        livroDTO.setTitulo(livro.getTitulo());
        livroDTO.setAutor(livro.getAutor());
        livroDTO.setGenero(livro.getGenero());
        livroDTO.setEstadoConservacao(livro.getEstadoConservacao());
        livroDTO.setDescricao(livro.getDescricao());
        livroDTO.setAnoPublicacao(livro.getAnoPublicacao());
        livroDTO.setDonoId(livro.getDono().getId());
        
        model.addAttribute("livroDTO", livroDTO);
        return "livro_form";
    }
    
    @PostMapping("/{id}/excluir")
    public String excluirLivro(@PathVariable("id") Long livroId, Principal principal) {
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName());
    
        livroService.deleteLivro(livroId, usuarioLogado);
    
        return "redirect:/livros";
    }

    @GetMapping("/{id}")
    public String detalhesLivro(@PathVariable("id") Long id, Model model, @Nullable Principal principal) {
        Livro livro = livroService.findLivroById(id);
        model.addAttribute("livro", livro);
        
        if (principal != null) {
            Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName());
            model.addAttribute("usuarioLogadoId", usuarioLogado.getId());
        }
        
        return "livro";
    }
}



