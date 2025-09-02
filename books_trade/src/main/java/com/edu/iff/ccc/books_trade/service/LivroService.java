package com.edu.iff.ccc.books_trade.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.iff.ccc.books_trade.entities.Livro;

@Service
public class LivroService {

    private List<Livro> livros = new ArrayList<>();
    private long idCounter = 1;

    public Livro saveLivro(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(idCounter++);
        }
        livros.add(livro);
        System.out.println("Livro salvo: " + livro.getTitulo() + " - Autor: " + livro.getAutor());
        return livro;
    }

    public Livro findLivroById(Long id) {
        return livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Livro> findAllLivros() {
        return livros;
    }
    
}
