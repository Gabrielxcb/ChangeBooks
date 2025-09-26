package com.edu.iff.ccc.books_trade.controller.view;

import com.edu.iff.ccc.books_trade.dto.LivroDTO;
import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.service.LivroService;
import com.edu.iff.ccc.books_trade.service.UsuarioService; // <-- Importante
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List; // <-- Importante

@Controller
@RequestMapping("/livros")
public class LivroViewController {

    private final LivroService livroService;
    // 1. DECLARAÇÃO DO CAMPO QUE ESTAVA FALTANDO
    private final UsuarioService usuarioService; 

    // 2. CONSTRUTOR ATUALIZADO PARA RECEBER O USUARIOSERVICE
    public LivroViewController(LivroService livroService, UsuarioService usuarioService) {
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarLivros(Model model) {
        model.addAttribute("livros", livroService.findAllLivros());
        return "livros"; // livros.html
    }

    @GetMapping("/novo")
    public String novoLivroForm(Model model) {
        // Agora o 'usuarioService' é conhecido e o erro desaparecerá
        List<UsuarioComum> todosOsUsuarios = usuarioService.findAllUsuariosComuns();
        model.addAttribute("livroDTO", new LivroDTO());
        model.addAttribute("usuarios", todosOsUsuarios);
        return "livro_form";
    }

    @PostMapping
    public String salvarLivro(@Valid @ModelAttribute("livroDTO") LivroDTO livroDTO,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            // Se houver erros, precisamos reenviar a lista de usuários para o formulário
            List<UsuarioComum> todosOsUsuarios = usuarioService.findAllUsuariosComuns();
            model.addAttribute("usuarios", todosOsUsuarios);
            return "livro_form";
        }

        Usuario dono = usuarioService.findUsuarioById(livroDTO.getDonoId())
                .orElseThrow(() -> new IllegalArgumentException("Dono do livro inválido. ID: " + livroDTO.getDonoId()));
        
        if(!(dono instanceof UsuarioComum)){
            throw new ClassCastException("O dono do livro deve ser um usuário comum.");
        }

        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setGenero(livroDTO.getGenero());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setEstadoConservacao(livroDTO.getEstadoConservacao());
        livro.setDono((UsuarioComum) dono);

        livroService.saveLivro(livro);

        return "redirect:/livros";
    }

    @GetMapping("/{id}")
    public String detalhesLivro(@PathVariable("id") Long id, Model model) {
        Livro livro = livroService.findLivroById(id);
        model.addAttribute("livro", livro);
        return "livro"; // livro.html
    }
}



