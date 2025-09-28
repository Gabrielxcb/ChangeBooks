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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/livros")
public class LivroRestController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para LER (GET) todos os livros
    @GetMapping
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
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable("id") Long id) {
        Livro livro = livroService.findLivroById(id);
        // Aqui ainda não estamos tratando o erro de "não encontrado", faremos isso na Etapa 3.
        
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
        
        return ResponseEntity.ok(dto);
    }

    // Endpoint para CRIAR (POST) um novo livro
    @PostMapping
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO, UriComponentsBuilder uriBuilder) {
        // Busca o usuário dono (neste exemplo, pegando o primeiro usuário comum como padrão)
        // Na Etapa 5 (Segurança), isso será substituído pelo usuário autenticado.
        UsuarioComum dono = (UsuarioComum) usuarioService.findUsuarioById(livroDTO.getDonoId())
                .orElseThrow(() -> new RuntimeException("Dono não encontrado"));

        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setGenero(livroDTO.getGenero());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setEstadoConservacao(livroDTO.getEstadoConservacao());

        dono.addLivro(livro);
        usuarioService.updateUsuario(dono);

        // Atualiza o DTO com o ID gerado
        livroDTO.setId(livro.getId());
        
        // Constrói a URI para o novo recurso criado
        URI uri = uriBuilder.path("/api/v1/livros/{id}").buildAndExpand(livro.getId()).toUri();
        
        // Retorna 201 Created com a URI no header Location e o objeto criado no corpo
        return ResponseEntity.created(uri).body(livroDTO);
    }
    
    // Endpoint para ATUALIZAR (PUT) um livro existente
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable("id") Long id, @Valid @RequestBody LivroDTO livroDTO) {
        // Na Etapa 5 (Segurança), adicionaremos a lógica para verificar se o usuário autenticado é o dono do livro.
        Usuario dono = usuarioService.findUsuarioById(livroDTO.getDonoId())
                .orElseThrow(() -> new RuntimeException("Dono não encontrado"));

        Livro livroAtualizado = livroService.updateLivro(id, livroDTO, dono);
        
        return ResponseEntity.ok(livroDTO);
    }
    
    // Endpoint para DELETAR (DELETE) um livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        // Na Etapa 5 (Segurança), adicionaremos a lógica para verificar se o usuário autenticado é o dono do livro.
        // Temporariamente, precisamos de um objeto 'Usuario' para passar para o método de serviço.
        // Vamos buscar o dono do livro para poder excluí-lo.
        Livro livro = livroService.findLivroById(id);
        Usuario dono = livro.getDono();

        livroService.deleteLivro(id, dono);
        
        // Retorna 204 No Content, indicando sucesso sem corpo na resposta
        return ResponseEntity.noContent().build();
    }
}
