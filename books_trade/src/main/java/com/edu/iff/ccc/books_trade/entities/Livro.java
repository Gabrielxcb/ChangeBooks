package com.edu.iff.ccc.books_trade.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="livros_table")
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotEmpty(message = "O título não pode ser vazio.")
    private String titulo;

    @NotEmpty(message = "O autor não pode ser vazio.")
    private String autor;

    private String genero;
    private String descricao;
    private int anoPublicacao; // novo campo

    public Livro() {}

    public Livro(Long id, String titulo, String autor, String genero, String descricao, int anoPublicacao) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.descricao = descricao;
        this.anoPublicacao = anoPublicacao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    @Override
    public int hashCode() { return Objects.hash(id, titulo); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Livro livro = (Livro) obj;
        return Objects.equals(id, livro.id) && Objects.equals(titulo, livro.titulo);
    }
}
