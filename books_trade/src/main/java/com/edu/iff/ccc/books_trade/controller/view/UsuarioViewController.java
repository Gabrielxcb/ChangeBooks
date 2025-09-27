package com.edu.iff.ccc.books_trade.controller.view;

import com.edu.iff.ccc.books_trade.dto.UsuarioDTO;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "usuarios";
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "usuario_form";
    }

    @PostMapping
    public String salvarUsuario(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "usuario_form";
        }

        UsuarioComum usuario = new UsuarioComum();
        if (usuarioDTO.getId() != null) {
            usuario.setId(usuarioDTO.getId());
        }
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTelefone(usuarioDTO.getTelefone());

        // ÚNICA MUDANÇA NESTA CLASSE: Chamando o método correto.
        usuarioService.criarNovoUsuario(usuario);

        return "redirect:/usuarios";
    }

    @GetMapping("/{id}")
    public String getPerfilUsuario(@PathVariable("id") Long id, Model model) {
        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioById(id);
        if (usuarioOptional.isPresent()) {
            model.addAttribute("usuario", usuarioOptional.get());
            return "usuario";
        } else {
            return "redirect:/usuarios";
        }
    }

    @GetMapping("/{id}/editar")
    public String editarPerfilUsuario(@PathVariable("id") Long id, Model model) {
        Optional<Usuario> usuarioOptional = usuarioService.findUsuarioById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNome(usuario.getNome());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setTelefone(usuario.getTelefone());

            model.addAttribute("usuarioDTO", usuarioDTO);
            return "usuario_form";
        } else {
            return "redirect:/usuarios";
        }
    }
}


