package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import com.edu.iff.ccc.books_trade.entities.Troca;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TrocaService {

    private final List<Troca> trocas = new ArrayList<>();
    private long idCounter = 1;

    public void criarTroca(PropostaTroca proposta) {
        Troca novaTroca = new Troca();
        novaTroca.setId(idCounter++);
        novaTroca.setPropostaTroca(proposta);
        novaTroca.setLivroEnviado(proposta.getLivroOfertado());
        novaTroca.setLivroRecebido(proposta.getLivroDesejado());
        novaTroca.setUsuarioOrigem(proposta.getRemetente());
        novaTroca.setUsuarioDestino(proposta.getDestinatario());
        novaTroca.setDataTroca(new Date());

        trocas.add(novaTroca);
        System.out.println("Troca registrada para a proposta ID " + proposta.getId());
    }
    
    public List<Troca> findAllTrocas() {
        return new ArrayList<>(trocas);
    }
}