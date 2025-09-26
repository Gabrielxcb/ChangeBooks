package com.edu.iff.ccc.books_trade.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LivroDTO {

    private Long id;

    @NotBlank(message = "O título do livro não pode ser vazio.")
    @Size(min = 1, max = 100, message = "O título deve ter entre 1 e 100 caracteres.")
    private String titulo;

    @NotBlank(message = "O autor do livro não pode ser vazio.")
    @Size(min = 1, max = 100, message = "O nome do autor deve ter entre 1 e 100 caracteres.")
    private String autor;

    @Size(max = 50, message = "O gênero deve ter no máximo 50 caracteres.")
    private String genero;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    @Min(value = 1, message = "O ano de publicação deve ser maior que 0.")
    private int anoPublicacao;

    // --- CAMPOS QUE FALTAVAM ---
    @NotBlank(message = "O estado de conservação não pode ser vazio.")
    private String estadoConservacao;

    @NotNull(message = "O dono do livro precisa ser selecionado.")
    private Long donoId;

    // --- Getters e Setters ---
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

    // --- Getters e Setters para os novos campos ---
    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }
}