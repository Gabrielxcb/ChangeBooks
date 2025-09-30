package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.exceptions.LivroNaoEncontradoException;
import com.edu.iff.ccc.books_trade.exceptions.PropostaNaoEncontradaException;
import com.edu.iff.ccc.books_trade.exceptions.UsuarioNaoEncontradoException;
import com.edu.iff.ccc.books_trade.exceptions.RegraDeNegocioException;
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

@Service
public class PropostaTrocaService {

    private final PropostaTrocaRepository propostaRepository;
    private final LivroRepository livroRepository; 
    private final UsuarioRepository usuarioRepository;
    private final TrocaService trocaService;

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
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro ofertado não encontrado. ID: " + livroOfertadoId));
        Livro livroDesejado = livroRepository.findById(livroDesejadoId)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro desejado não encontrado. ID: " + livroDesejadoId));

        Usuario remetente = usuarioRepository.findById(remetenteId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário remetente não encontrado. ID: " + remetenteId));
        Usuario destinatario = usuarioRepository.findById(destinatarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário destinatário não encontrado. ID: " + destinatarioId));

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
    public PropostaTroca findPropostaById(Long id) { 
        return propostaRepository.findById(id)
            .orElseThrow(() -> new PropostaNaoEncontradaException("Proposta não encontrada com o ID: " + id));
    }

    @Transactional
    public PropostaTroca aceitarProposta(Long propostaId) {
        PropostaTroca proposta = this.findPropostaById(propostaId);

        if (proposta.getStatus() == StatusProposta.PENDENTE) {
            proposta.setStatus(StatusProposta.ACEITA);
            
            trocaService.criarTroca(proposta);
            
            Livro livroOfertado = proposta.getLivroOfertado();
            Livro livroDesejado = proposta.getLivroDesejado();
            
            livroOfertado.setDisponivel(false);
            livroDesejado.setDisponivel(false);
            
            livroRepository.save(livroOfertado);
            livroRepository.save(livroDesejado);
            
            return propostaRepository.save(proposta);
        }
        throw new RegraDeNegocioException("Esta proposta não pode mais ser aceita.");
    }

    @Transactional
    public PropostaTroca recusarProposta(Long propostaId) {
        PropostaTroca proposta = this.findPropostaById(propostaId);

        if (proposta.getStatus() == StatusProposta.PENDENTE) {
            proposta.setStatus(StatusProposta.RECUSADA);
            return propostaRepository.save(proposta);
        }
        throw new RegraDeNegocioException("Esta proposta não pode mais ser recusada.");
    }

    @Transactional(readOnly = true)
    public List<PropostaTroca> findAll() {
        return propostaRepository.findAll();
    }

}