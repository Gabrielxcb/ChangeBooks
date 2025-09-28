package com.edu.iff.ccc.books_trade.controller.restapi;

import com.edu.iff.ccc.books_trade.dto.LivroDTO;
import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.service.LivroService;
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
@RequestMapping("/api/v1/livros")
@Tag(name = "Livros", description = "Endpoints para gerenciamento de livros") // Agrupa todos os endpoints sob "Livros"
public class LivroRestController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para LER (GET) todos os livros
    @GetMapping
    @Operation(summary = "Lista todos os livros disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        List<Livro> livros = livroService.findAllLivros();
        // Convertendo a lista de Entidades para uma lista de DTOs
        List<LivroDTO> livrosDTO = new ArrayList<>();
        for (Livro livro : livros) {
            LivroDTO dto = new LivroDTO();
            dto.setId(livro.getId());
            dto.setTitulo(livro.getTitulo());
            dto.setAutor(livro.getAutor());
            dto.setGenero(livro.getGenero());
            dto.setDescricao(livro.getDescricao());
            dto.setAnoPublicacao(livro.getAnoPublicacao());
            dto.setEstadoConservacao(livro.getEstadoConservacao());
            if (livro.getDono() != null) {
                dto.setDonoId(livro.getDono().getId());
            }
            livrosDTO.add(dto);
        }
        return ResponseEntity.ok(livrosDTO);
    }

    // Endpoint para LER (GET) um livro específico por ID
    @GetMapping("/{id}")
    @Operation(summary = "Busca um livro por seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado com o ID fornecido")
    })
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable("id") Long id) {
        // MUDANÇA: Busca direta. @ControllerAdvice trata o "não encontrado".
        Livro livro = livroService.findLivroById(id);
        
        LivroDTO dto = new LivroDTO();
        // ... (lógica de conversão para DTO)
        return ResponseEntity.ok(dto);
    }

    // Endpoint para CRIAR (POST) um novo livro
    @PostMapping
    @Operation(summary = "Cadastra um novo livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do livro")
    })
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO, UriComponentsBuilder uriBuilder) {
        // MUDANÇA: Busca direta. @ControllerAdvice trata o "não encontrado".
        UsuarioComum dono = (UsuarioComum) usuarioService.findUsuarioById(livroDTO.getDonoId());

        Livro livro = new Livro();
        // ... (lógica de criação do livro)
        dono.addLivro(livro);
        usuarioService.updateUsuario(dono);
        
        livroDTO.setId(livro.getId());
        URI uri = uriBuilder.path("/api/v1/livros/{id}").buildAndExpand(livro.getId()).toUri();
        return ResponseEntity.created(uri).body(livroDTO);
    }
    
    // Endpoint para ATUALIZAR (PUT) um livro existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um livro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado com o ID fornecido"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do livro")
    })
    public ResponseEntity<LivroDTO> atualizar(@PathVariable("id") Long id, @Valid @RequestBody LivroDTO livroDTO) {
        // MUDANÇA: Busca direta. @ControllerAdvice trata o "não encontrado".
        Usuario dono = usuarioService.findUsuarioById(livroDTO.getDonoId());

        Livro livroAtualizado = livroService.updateLivro(id, livroDTO, dono);
        
        // Converte a entidade atualizada para DTO para a resposta
        LivroDTO dtoDeResposta = new LivroDTO();
        // ... (lógica de conversão)
        return ResponseEntity.ok(dtoDeResposta);
    }
    
    // Endpoint para DELETAR (DELETE) um livro
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um livro por seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado com o ID fornecido")
    })
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        // MUDANÇA: Busca direta. @ControllerAdvice trata o "não encontrado".
        Livro livro = livroService.findLivroById(id);
        Usuario dono = livro.getDono();

        livroService.deleteLivro(id, dono);
        
        return ResponseEntity.noContent().build();
    }
}
