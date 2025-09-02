package com.edu.iff.ccc.books_trade.controller.view;

import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.dto.LivroDTO;
import com.edu.iff.ccc.books_trade.service.LivroService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroViewController {

    private final LivroService livroService;

    public LivroViewController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public String listarLivros(Model model) {
        model.addAttribute("livros", livroService.findAllLivros());
        return "livros"; // livros.html
    }

    @GetMapping("/novo")
    public String novoLivroForm(Model model) {
        model.addAttribute("livroDTO", new LivroDTO());
        return "livro_form"; // livro_form.html
    }

    @PostMapping
    public String salvarLivro(@Valid @ModelAttribute("livroDTO") LivroDTO livroDTO, 
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "livro_form"; // retorna o formulário com mensagens de erro
        }

        // Conversão DTO -> Entity
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setGenero(livroDTO.getGenero());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());

        livroService.saveLivro(livro);

        return "redirect:/livros"; // redireciona para a lista de livros
    }

    @GetMapping("/{id}")
    public String detalhesLivro(@PathVariable("id") Long id, Model model) {
        Livro livro = livroService.findLivroById(id);
        model.addAttribute("livro", livro);
        return "livro"; // livro.html
    }
}



