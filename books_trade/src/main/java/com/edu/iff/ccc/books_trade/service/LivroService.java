package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.Livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.iff.ccc.books_trade.repository.LivroRepository;
import com.edu.iff.ccc.books_trade.exception.LivroNaoEncontrado;

@Service
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    public void saveLivro(Livro livro) {
        livroRepository.save(livro);
    }

    public Livro findLivroById(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontrado("Livro com ID " + id + " não encontrado."));
    }

    public ArrayList<Livro> findAllLivros() {
        return (ArrayList<Livro>) livroRepository.findAll();
    }

    public void updateLivro(Long id, Livro livro) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNaoEncontrado("Livro com ID " + id + " não encontrado.");
        }
        livro.setId(id);  
        livroRepository.save(livro);
    }

    public void deleteLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNaoEncontrado("Livro com ID " + id + " não encontrado.");
        }
        livroRepository.deleteById(id);
    }
}
