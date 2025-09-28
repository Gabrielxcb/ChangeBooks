package com.edu.iff.ccc.books_trade.controller.restapi;

import com.edu.iff.ccc.books_trade.dto.PropostaTrocaDTO;
import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import com.edu.iff.ccc.books_trade.service.PropostaTrocaService;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/propostas")
@Tag(name = "Propostas de Troca", description = "Endpoints para criar e gerenciar propostas de troca")
public class PropostaTrocaRestController {

    @Autowired
    private PropostaTrocaService propostaService;

    @GetMapping
    @Operation(summary = "Lista todas as propostas de troca do sistema")
    @ApiResponse(responseCode = "200", description = "Lista de propostas retornada com sucesso")
    public ResponseEntity<List<PropostaTrocaDTO>> listarTodas() {
        // A chamada agora deve funcionar sem erros.
        List<PropostaTroca> propostas = propostaService.findAll();
        
        List<PropostaTrocaDTO> propostasDTO = new ArrayList<>();
        for (PropostaTroca p : propostas) {
            PropostaTrocaDTO dto = new PropostaTrocaDTO();
            dto.setId(p.getId());
            dto.setLivroOfertadoId(p.getLivroOfertado().getId());
            dto.setLivroDesejadoId(p.getLivroDesejado().getId());
            dto.setRemetenteId(p.getRemetente().getId());
            dto.setDestinatarioId(p.getDestinatario().getId());
            propostasDTO.add(dto);
        }
        return ResponseEntity.ok(propostasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma proposta de troca por seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proposta encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Proposta não encontrada com o ID fornecido")
    })
    public ResponseEntity<PropostaTrocaDTO> buscarPorId(@PathVariable("id") Long id) {
        PropostaTroca p = propostaService.findPropostaById(id);
        
        PropostaTrocaDTO dto = new PropostaTrocaDTO();
        dto.setId(p.getId());
        dto.setLivroOfertadoId(p.getLivroOfertado().getId());
        dto.setLivroDesejadoId(p.getLivroDesejado().getId());
        dto.setRemetenteId(p.getRemetente().getId());
        dto.setDestinatarioId(p.getDestinatario().getId());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Cria uma nova proposta de troca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proposta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação ou IDs inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Recurso (livro ou usuário) não encontrado")
    })
    public ResponseEntity<PropostaTrocaDTO> criar(@Valid @RequestBody PropostaTrocaDTO propostaDTO, UriComponentsBuilder uriBuilder) {
        PropostaTroca novaProposta = propostaService.criarProposta(
                propostaDTO.getLivroOfertadoId(),
                propostaDTO.getLivroDesejadoId(),
                propostaDTO.getRemetenteId(),
                propostaDTO.getDestinatarioId()
        );
        propostaDTO.setId(novaProposta.getId());
        URI uri = uriBuilder.path("/api/v1/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri();
        return ResponseEntity.created(uri).body(propostaDTO);
    }

    @PostMapping("/{id}/aceitar")
    @Operation(summary = "Aceita uma proposta de troca pendente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proposta aceita com sucesso"),
        @ApiResponse(responseCode = "404", description = "Proposta não encontrada com o ID fornecido")
    })
    public ResponseEntity<Void> aceitar(@PathVariable("id") Long id) {
        propostaService.aceitarProposta(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/recusar")
    @Operation(summary = "Recusa uma proposta de troca pendente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proposta recusada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Proposta não encontrada com o ID fornecido")
    })
    public ResponseEntity<Void> recusar(@PathVariable("id") Long id) {
        propostaService.recusarProposta(id);
        return ResponseEntity.ok().build();
    }
}
