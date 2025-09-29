package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import com.edu.iff.ccc.books_trade.entities.Troca;
import com.edu.iff.ccc.books_trade.repository.TrocaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TrocaService {

    private final TrocaRepository trocaRepository;

    @Autowired
    public TrocaService(TrocaRepository trocaRepository) {
        this.trocaRepository = trocaRepository;
    }

    @Transactional
    public Troca criarTroca(PropostaTroca proposta) {
        Troca novaTroca = new Troca();
        novaTroca.setPropostaTroca(proposta);
        novaTroca.setLivroEnviado(proposta.getLivroOfertado());
        novaTroca.setLivroRecebido(proposta.getLivroDesejado());
        novaTroca.setUsuarioOrigem(proposta.getRemetente());
        novaTroca.setUsuarioDestino(proposta.getDestinatario());
        novaTroca.setDataTroca(new Date());

        return trocaRepository.save(novaTroca);
    }

    @Transactional(readOnly = true)
    public List<Troca> findAllTrocas() {
        return trocaRepository.findAll();
    }
}