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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setTelefone(usuario.getTelefone());
            
            usuariosDTO.add(dto);
        }
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido")
    })
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findUsuarioById(id);
        
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        
        return ResponseEntity.ok(dto);
    }

    
    @PostMapping
    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação ou e-mail já existente")
    })
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder uriBuilder) {
        UsuarioComum usuario = new UsuarioComum();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTelefone(usuarioDTO.getTelefone());

        Usuario novoUsuario = usuarioService.criarNovoUsuario(usuario);

        usuarioDTO.setId(novoUsuario.getId());
        usuarioDTO.setSenha(null);

        URI uri = uriBuilder.path("/api/v1/usuarios/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do usuário")
    })
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        
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
