package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.Livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.webdev.entities.Livro;
import br.edu.iff.ccc.webdev.repository.LivroRepository;
import com.edu.iff.ccc.books_trade.exception.LivroNaoEncontrado;

@Service
public class LivroService {

    @Autowired
    LivroRepository livroRepository;
    private final List<Livro> livros = new ArrayList<>();
    private long idCounter = 1;

    public void saveLivro(Livro livro) {
       livroRepository.save(livro);
    }

    public Livro findLivroById(Long id) {
       return livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontrado("Livro com ID " + id + " não encontrado."));
    }

    public Livro findLivroById(Long id) {
        return livroRepository.findById(id).isPresent() ? livroRepository.findById(id).get() : null;
    }

    public List<Livro> findAllLivros() {
        return (ArrayList<Livro>) livroRepository.findAll();
    }
}