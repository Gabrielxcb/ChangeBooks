package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import com.edu.iff.ccc.books_trade.entities.StatusProposta;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.repository.LivroRepository;
import com.edu.iff.ccc.books_trade.repository.PropostaTrocaRepository;
import com.edu.iff.ccc.books_trade.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PropostaTrocaService {

    private final PropostaTrocaRepository propostaRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrocaService trocaService; // Injetando o TrocaService

    @Autowired
    public PropostaTrocaService(PropostaTrocaRepository propostaRepository, LivroRepository livroRepository,
                                UsuarioRepository usuarioRepository, TrocaService trocaService) {
        this.propostaRepository = propostaRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.trocaService = trocaService;
    }

    @Transactional
    public PropostaTroca criarProposta(Long livroOfertadoId, Long livroDesejadoId, Long remetenteId, Long destinatarioId) {
        Livro livroOfertado = livroRepository.findById(livroOfertadoId)
                .orElseThrow(() -> new IllegalArgumentException("Livro ofertado inválido."));
        Livro livroDesejado = livroRepository.findById(livroDesejadoId)
                .orElseThrow(() -> new IllegalArgumentException("Livro desejado inválido."));

        Usuario remetente = usuarioRepository.findById(remetenteId)
                .orElseThrow(() -> new IllegalArgumentException("Remetente inválido."));
        Usuario destinatario = usuarioRepository.findById(destinatarioId)
                .orElseThrow(() -> new IllegalArgumentException("Destinatário inválido."));

        if (!(remetente instanceof UsuarioComum) || !(destinatario instanceof UsuarioComum)) {
            throw new ClassCastException("Remetente e Destinatário devem ser Usuários Comuns.");
        }

        PropostaTroca novaProposta = new PropostaTroca();
        novaProposta.setLivroOfertado(livroOfertado);
        novaProposta.setLivroDesejado(livroDesejado);
        novaProposta.setRemetente((UsuarioComum) remetente);
        novaProposta.setDestinatario((UsuarioComum) destinatario);
        novaProposta.setStatus(StatusProposta.PENDENTE);
        novaProposta.setDataEnvio(new Date());

        return propostaRepository.save(novaProposta);
    }

    @Transactional(readOnly = true)
    public List<PropostaTroca> findPropostasByUsuarioId(Long usuarioId) {
        return propostaRepository.findByRemetenteIdOrDestinatarioId(usuarioId, usuarioId);
    }

    @Transactional(readOnly = true)
    public Optional<PropostaTroca> findPropostaById(Long id) {
        return propostaRepository.findById(id);
    }

    @Transactional
    public PropostaTroca aceitarProposta(Long propostaId) {
        PropostaTroca proposta = propostaRepository.findById(propostaId)
                .orElseThrow(() -> new IllegalArgumentException("Proposta não encontrada."));

        if (proposta.getStatus() == StatusProposta.PENDENTE) {
            proposta.setStatus(StatusProposta.ACEITA);
            
            // Lógica para criar a Troca (agora está ativa)
            trocaService.criarTroca(proposta);
            
            // TODO: Adicionar lógica para marcar livros como indisponíveis
            
            return propostaRepository.save(proposta);
        }
        throw new IllegalStateException("Esta proposta não pode mais ser aceita.");
    }

    @Transactional
    public PropostaTroca recusarProposta(Long propostaId) {
        PropostaTroca proposta = propostaRepository.findById(propostaId)
                .orElseThrow(() -> new IllegalArgumentException("Proposta não encontrada."));

        if (proposta.getStatus() == StatusProposta.PENDENTE) {
            proposta.setStatus(StatusProposta.RECUSADA);
            return propostaRepository.save(proposta);
        }
        throw new IllegalStateException("Esta proposta não pode mais ser recusada.");
    }

    public List<PropostaTroca> findAll() {
    return propostaRepository.findAll();
    }

}