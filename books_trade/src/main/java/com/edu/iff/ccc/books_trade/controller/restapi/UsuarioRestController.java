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
        // MUDANÇA: A busca agora é direta.
        // Se o usuário não for encontrado, o serviço lança a exceção e o @ControllerAdvice a captura,
        // retornando um 404 Not Found com JSON automaticamente.
        Usuario usuario = usuarioService.findUsuarioById(id);
        
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
        // MUDANÇA: A busca agora é direta.
        // O @ControllerAdvice também funcionará aqui se o ID for inválido.
        Usuario usuario = usuarioService.findUsuarioById(id);
        
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefone(usuarioDTO.getTelefone());

        Usuario usuarioAtualizado = usuarioService.updateUsuario(usuario);

        usuarioDTO.setId(usuarioAtualizado.getId());
        usuarioDTO.setSenha(null);

        return ResponseEntity.ok(usuarioDTO);
    }
}
