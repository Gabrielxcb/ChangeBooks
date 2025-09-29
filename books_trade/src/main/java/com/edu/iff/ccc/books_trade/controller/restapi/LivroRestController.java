package com.edu.iff.ccc.books_trade.controller.restapi; // ou controller.restapi

import com.edu.iff.ccc.books_trade.dto.LivroDTO;
import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.service.LivroService;
import com.edu.iff.ccc.books_trade.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/livros")
@Tag(name = "Livros", description = "Endpoints para gerenciamento de livros")
public class LivroRestController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Lista todos os livros disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        List<Livro> livros = livroService.findAllLivros();
        // Usando o método helper para converter a lista
        List<LivroDTO> livrosDTO = livros.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(livrosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um livro por seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado com o ID fornecido")
    })
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable("id") Long id) {
        Livro livro = livroService.findLivroById(id);
        // Usando o método helper para converter a entidade em DTO
        return ResponseEntity.ok(toDTO(livro));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do livro")
    })
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO, UriComponentsBuilder uriBuilder) {
        UsuarioComum dono = (UsuarioComum) usuarioService.findUsuarioById(livroDTO.getDonoId());

        // Lógica de preenchimento que faltava
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setGenero(livroDTO.getGenero());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setEstadoConservacao(livroDTO.getEstadoConservacao());

        dono.addLivro(livro);
        usuarioService.updateUsuario(dono);
        
        URI uri = uriBuilder.path("/api/v1/livros/{id}").buildAndExpand(livro.getId()).toUri();
        // Usando o método helper para criar a resposta com o DTO correto
        return ResponseEntity.created(uri).body(toDTO(livro));
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
        Usuario dono = usuarioService.findUsuarioById(livroDTO.getDonoId());
        Livro livroAtualizado = livroService.updateLivro(id, livroDTO, dono);
        // Usando o método helper para criar a resposta com o DTO correto
        return ResponseEntity.ok(toDTO(livroAtualizado));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um livro por seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado com o ID fornecido")
    })
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        Livro livro = livroService.findLivroById(id);
        Usuario dono = livro.getDono();
        livroService.deleteLivro(id, dono);
        return ResponseEntity.noContent().build();
    }
    
    // --- MÉTODO HELPER PRIVADO PARA CONVERSÃO ---
    private LivroDTO toDTO(Livro livro) {
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
        return dto;
    }
}