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

    // MÉTODO MODIFICADO: Agora ele lida com CRIAR e ATUALIZAR
    @PostMapping
    public String salvarOuAtualizarLivro(@Valid @ModelAttribute("livroDTO") LivroDTO livroDTO,
                                         BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "livro_form";
        }
        
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (livroDTO.getId() == null) {
            // Lógica para CRIAR um novo livro
            UsuarioComum donoComum = (UsuarioComum) usuarioLogado;
            Livro novoLivro = new Livro();
            // ... (preenche o novoLivro com dados do DTO)
            novoLivro.setTitulo(livroDTO.getTitulo());
            novoLivro.setAutor(livroDTO.getAutor());
            novoLivro.setGenero(livroDTO.getGenero());
            novoLivro.setDescricao(livroDTO.getDescricao());
            novoLivro.setAnoPublicacao(livroDTO.getAnoPublicacao());
            novoLivro.setEstadoConservacao(livroDTO.getEstadoConservacao());
            
            donoComum.addLivro(novoLivro);
            usuarioService.updateUsuario(donoComum);
        } else {
            // Lógica para ATUALIZAR um livro existente
            livroService.updateLivro(livroDTO.getId(), livroDTO, usuarioLogado);
        }
        
        return "redirect:/livros";
    }

    // NOVO MÉTODO: Exibe o formulário de edição
    @GetMapping("/{id}/editar")
    public String editarLivroForm(@PathVariable("id") Long livroId, Model model, Principal principal) {
        Livro livro = livroService.findLivroById(livroId);
        
        // Verificação de segurança: o usuário logado é o dono do livro?
        if (!livro.getDono().getEmail().equals(principal.getName())) {
            // Se não for, redireciona ou mostra uma página de erro
            return "redirect:/livros"; 
        }

        // Converte a Entidade para DTO para popular o formulário
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
    
    // NOVO MÉTODO: Processa a exclusão
    @PostMapping("/{id}/excluir")
    public String excluirLivro(@PathVariable("id") Long livroId, Principal principal) {
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        livroService.deleteLivro(livroId, usuarioLogado);
        
        return "redirect:/livros";
    }

    @GetMapping("/{id}")
    public String detalhesLivro(@PathVariable("id") Long id, Model model, @Nullable Principal principal) {
    
        // Busca o livro normalmente
        Livro livro = livroService.findLivroById(id);
        model.addAttribute("livro", livro);
    
        // Verifica se há um usuário logado
        if (principal != null) {
            // Se houver, busca o ID dele e envia para o template
            Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            model.addAttribute("usuarioLogadoId", usuarioLogado.getId());
        }
    
        return "livro"; // livro.html
    }
}



