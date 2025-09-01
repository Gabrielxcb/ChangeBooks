package com.edu.iff.ccc.books_trade.dto;

import jakarta.validation.constraints.NotNull;

public class PropostaTrocaDTO {

    private Long id;

    @NotNull(message = "O ID do livro ofertado não pode ser nulo.")
    private Long livroOfertadoId;

    @NotNull(message = "O ID do livro desejado não pode ser nulo.")
    private Long livroDesejadoId;

    @NotNull(message = "O ID do remetente não pode ser nulo.")
    private Long remetenteId;

    @NotNull(message = "O ID do destinatário não pode ser nulo.")
    private Long destinatarioId;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivroOfertadoId() {
        return livroOfertadoId;
    }

    public void setLivroOfertadoId(Long livroOfertadoId) {
        this.livroOfertadoId = livroOfertadoId;
    }

    public Long getLivroDesejadoId() {
        return livroDesejadoId;
    }

    public void setLivroDesejadoId(Long livroDesejadoId) {
        this.livroDesejadoId = livroDesejadoId;
    }

    public Long getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(Long remetenteId) {
        this.remetenteId = remetenteId;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }
}