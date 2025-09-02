package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.Livro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final List<Livro> livros = new ArrayList<>();
    private long idCounter = 1;

    public void saveLivro(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(idCounter++);
        }
        livros.add(livro);
        System.out.println("Livro salvo: " + livro.getTitulo() + " - Autor: " + livro.getAutor());
    }

    public Livro findLivroById(Long id) {
        return livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Livro> findAllLivros() {
        if (livros.isEmpty()) {
            Livro livro1 = new Livro("Rainha Vermelha", "Victoria Aveyard", "Fantasia", "Usado", null, "Descrição de Rainha Vermelha", 2015);
            livro1.setId(idCounter++);
            livros.add(livro1);

            Livro livro2 = new Livro("A Cor Púrpura", "Alice Walker", "Ficção", "Novo", null, "Descrição de A Cor Púrpura", 1982);
            livro2.setId(idCounter++);
            livros.add(livro2);

            Livro livro3 = new Livro("1984", "George Orwell", "Distopia", "Semi-novo", null, "Descrição de 1984", 1949);
            livro3.setId(idCounter++);
            livros.add(livro3);
        }
        return new ArrayList<>(livros);
    }
}