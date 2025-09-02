package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import com.edu.iff.ccc.books_trade.entities.StatusProposta;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropostaTrocaService {

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    private final List<PropostaTroca> propostas = new ArrayList<>();
    private long idCounter = 1;

    public PropostaTroca criarProposta(Long livroOfertadoId, Long livroDesejadoId, Long remetenteId, Long destinatarioId) {
        Livro livroOfertado = livroService.findLivroById(livroOfertadoId);
        Livro livroDesejado = livroService.findLivroById(livroDesejadoId);
        
        Optional<UsuarioComum> remetente = usuarioService.findUsuarioById(remetenteId)
                .filter(u -> u instanceof UsuarioComum)
                .map(u -> (UsuarioComum) u);
        
        Optional<UsuarioComum> destinatario = usuarioService.findUsuarioById(destinatarioId)
                .filter(u -> u instanceof UsuarioComum)
                .map(u -> (UsuarioComum) u);
        
        if (livroOfertado == null || livroDesejado == null || !remetente.isPresent() || !destinatario.isPresent()) {
            throw new IllegalArgumentException("IDs de livro ou usuário inválidos.");
        }

        PropostaTroca novaProposta = new PropostaTroca();
        novaProposta.setId(idCounter++);
        novaProposta.setLivroOfertado(livroOfertado);
        novaProposta.setLivroDesejado(livroDesejado);
        novaProposta.setRemetente(remetente.get());
        novaProposta.setDestinatario(destinatario.get());
        novaProposta.setStatus(StatusProposta.PENDENTE);
        novaProposta.setDataEnvio(new Date());

        propostas.add(novaProposta);
        System.out.println("Proposta de troca criada com sucesso! ID: " + novaProposta.getId());
        return novaProposta;
    }

    public List<PropostaTroca> findPropostasByUsuarioId(Long usuarioId) {
        return propostas.stream()
                .filter(p -> p.getRemetente().getId().equals(usuarioId) || p.getDestinatario().getId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    public Optional<PropostaTroca> findPropostaById(Long id) {
        return propostas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
    
    public PropostaTroca aceitarProposta(Long propostaId) {
        Optional<PropostaTroca> propostaOpt = findPropostaById(propostaId);
        if (propostaOpt.isPresent() && propostaOpt.get().getStatus() == StatusProposta.PENDENTE) {
            PropostaTroca proposta = propostaOpt.get();
            proposta.setStatus(StatusProposta.ACEITA);
            System.out.println("Proposta ID " + propostaId + " aceita.");
            // Lógica para criar a Troca
            // trocaService.criarTroca(proposta);
            return proposta;
        }
        return null;
    }

    public PropostaTroca recusarProposta(Long propostaId) {
        Optional<PropostaTroca> propostaOpt = findPropostaById(propostaId);
        if (propostaOpt.isPresent() && propostaOpt.get().getStatus() == StatusProposta.PENDENTE) {
            PropostaTroca proposta = propostaOpt.get();
            proposta.setStatus(StatusProposta.RECUSADA);
            System.out.println("Proposta ID " + propostaId + " recusada.");
            return proposta;
        }
        return null;
    }
}