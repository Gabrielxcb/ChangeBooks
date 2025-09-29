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

@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaTrocaRestController {

    @Autowired
    private PropostaTrocaService propostaService;

    @GetMapping
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
    public ResponseEntity<Void> aceitar(@PathVariable("id") Long id) {
        propostaService.aceitarProposta(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/recusar")
    public ResponseEntity<Void> recusar(@PathVariable("id") Long id) {
        propostaService.recusarProposta(id);
        return ResponseEntity.ok().build();
    }
}
