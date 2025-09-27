package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Livro saveLivro(Livro livro) {
        // A lógica de ID é gerenciada pelo banco de dados agora
        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro findLivroById(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Livro> findAllLivros() {
        return livroRepository.findAll();
    }
    
    @Transactional
    public void deleteLivroById(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado com o ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Livro> findLivrosByDonoId(Long donoId) {
        return livroRepository.findByDonoId(donoId);
    }

    @Transactional(readOnly = true)
    public List<Livro> findLivrosDisponiveis(Long meuId) {
        return livroRepository.findByDonoIdNot(meuId);
    }
}