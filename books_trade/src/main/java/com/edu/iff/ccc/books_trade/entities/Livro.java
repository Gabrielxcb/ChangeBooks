package com.edu.iff.ccc.books_trade.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "livros")
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título do livro não pode ser vazio.")
    @Size(min = 1, max = 150, message = "O título deve ter entre 1 e 150 caracteres.")
    private String titulo;

    @NotBlank(message = "O nome do autor não pode ser vazio.")
    private String autor;

    @NotBlank(message = "O gênero do livro não pode ser vazio.")
    private String genero;

    @NotBlank(message = "O estado de conservação não pode ser vazio.")
    private String estadoConservacao;

    private boolean disponivel = true;
    
    private String descricao;
    
    private int anoPublicacao;

    
    @ManyToOne
    @JoinColumn(name = "dono_id")
    private UsuarioComum dono;

    public Livro() {
    }

    public Livro(String titulo, String autor, String genero, String estadoConservacao, UsuarioComum dono, String descricao, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.estadoConservacao = estadoConservacao;
        this.dono = dono;
        this.descricao = descricao;
        this.anoPublicacao = anoPublicacao;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public UsuarioComum getDono() {
        return dono;
    }

    public void setDono(UsuarioComum dono) {
        this.dono = dono;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
}