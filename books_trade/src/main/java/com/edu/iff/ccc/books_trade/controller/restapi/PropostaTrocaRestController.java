package com.edu.iff.ccc.books_trade.controller.restapi;

import com.edu.iff.ccc.books_trade.dto.PropostaTrocaDTO;
import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import com.edu.iff.ccc.books_trade.service.PropostaTrocaService;
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
@RequestMapping("/api/v1/propostas")
public class PropostaTrocaRestController {

    @Autowired
    private PropostaTrocaService propostaService;

    // GET para listar todas as propostas
    // TODO: Na Etapa 5, este endpoint deve ser modificado para retornar apenas as propostas do usuário autenticado.
    @GetMapping
    public ResponseEntity<List<PropostaTrocaDTO>> listarTodas() {
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

    // GET para buscar uma proposta por ID
    @GetMapping("/{id}")
    public ResponseEntity<PropostaTrocaDTO> buscarPorId(@PathVariable("id") Long id) {
        Optional<PropostaTroca> propostaOpt = propostaService.findPropostaById(id);
        if (propostaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PropostaTroca p = propostaOpt.get();
        PropostaTrocaDTO dto = new PropostaTrocaDTO();
        dto.setId(p.getId());
        dto.setLivroOfertadoId(p.getLivroOfertado().getId());
        dto.setLivroDesejadoId(p.getLivroDesejado().getId());
        dto.setRemetenteId(p.getRemetente().getId());
        dto.setDestinatarioId(p.getDestinatario().getId());
        return ResponseEntity.ok(dto);
    }

    // POST para criar uma nova proposta
    @PostMapping
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

    // Endpoint de AÇÃO para aceitar uma proposta
    @PostMapping("/{id}/aceitar")
    public ResponseEntity<Void> aceitar(@PathVariable("id") Long id) {
        propostaService.aceitarProposta(id);
        return ResponseEntity.ok().build(); // Retorna 200 OK
    }

    // Endpoint de AÇÃO para recusar uma proposta
    @PostMapping("/{id}/recusar")
    public ResponseEntity<Void> recusar(@PathVariable("id") Long id) {
        propostaService.recusarProposta(id);
        return ResponseEntity.ok().build(); // Retorna 200 OK
    }
}
