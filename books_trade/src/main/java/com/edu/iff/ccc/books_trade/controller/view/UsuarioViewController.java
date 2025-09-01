package com.edu.iff.ccc.books_trade.controller.view;

import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    public UsuarioViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> listaDeUsuarios = usuarioService.findAllUsuarios();
        model.addAttribute("usuarios", listaDeUsuarios);
        return "usuarios"; // usuarios.html
    }

    @GetMapping("/{id}")
    public String getPerfilUsuario(@PathVariable("id") Long id, Model model) {
        // Usa o service para buscar um usuário pelo ID
        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioById(id);

        // Verifica se o usuário foi encontrado
        if (usuarioOptional.isPresent()) {
            // Se encontrou, adiciona o objeto Usuario ao model
            model.addAttribute("usuario", usuarioOptional.get());
            return "usuario"; // Renderiza a página de detalhes do usuário
        } else {
            // Se não encontrou, redireciona para a lista de usuários
            return "redirect:/usuarios";
        }
    }

    @GetMapping("/{id}/editar")
    public String editarPerfilUsuario(@PathVariable("id") Long id, Model model) {
        // Usa o service para buscar o usuário que será editado
        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioById(id);

        // Verifica se o usuário foi encontrado
        if (usuarioOptional.isPresent()) {
            // Se encontrou, adiciona o objeto ao model para preencher o formulário
            model.addAttribute("usuario", usuarioOptional.get());
            return "usuario_form"; // Renderiza o formulário de edição
        } else {
            // Se não encontrou, redireciona para a lista de usuários
            return "redirect:/usuarios";
        }
    }
}


