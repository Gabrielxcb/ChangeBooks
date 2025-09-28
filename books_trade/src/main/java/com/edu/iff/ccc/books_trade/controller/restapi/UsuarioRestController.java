package com.edu.iff.ccc.books_trade.controller.restapi;

import com.edu.iff.ccc.books_trade.dto.UsuarioDTO;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    // GET para listar todos os usuários
    // CUIDADO: Em produção, este endpoint deveria ser protegido para admins.
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setTelefone(usuario.getTelefone());
            // A senha NUNCA é retornada
            usuariosDTO.add(dto);
        }
        return ResponseEntity.ok(usuariosDTO);
    }

    // GET para buscar um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.findUsuarioById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
        }
        Usuario usuario = usuarioOpt.get();
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        
        return ResponseEntity.ok(dto);
    }

    // POST para criar um novo usuário (cadastro)
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder uriBuilder) {
        UsuarioComum usuario = new UsuarioComum();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha()); // Senha em texto plano, o serviço irá criptografar
        usuario.setTelefone(usuarioDTO.getTelefone());

        Usuario novoUsuario = usuarioService.criarNovoUsuario(usuario);

        usuarioDTO.setId(novoUsuario.getId());
        usuarioDTO.setSenha(null); // Remove a senha da resposta

        URI uri = uriBuilder.path("/api/v1/usuarios/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    // PUT para atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioService.findUsuarioById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefone(usuarioDTO.getTelefone());
        // Note que não alteramos a senha aqui. Isso geralmente é feito em um endpoint separado.

        Usuario usuarioAtualizado = usuarioService.updateUsuario(usuario);

        usuarioDTO.setId(usuarioAtualizado.getId());
        usuarioDTO.setSenha(null);

        return ResponseEntity.ok(usuarioDTO);
    }
}
