package com.edu.iff.ccc.books_trade.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("COMUM")
public class UsuarioComum extends Usuario {

    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Livro> livrosCadastrados;

    @OneToMany(mappedBy = "remetente")
    private List<PropostaTroca> propostasEnviadas;

    @OneToMany(mappedBy = "destinatario")
    private List<PropostaTroca> propostasRecebidas;

    public UsuarioComum() {
        super();
    }

    public UsuarioComum(String nome, String email, String senha, String telefone) {
        super(nome, email, senha, telefone);
    }

    public List<Livro> getLivrosCadastrados() {
        return livrosCadastrados;
    }

    public void setLivrosCadastrados(List<Livro> livrosCadastrados) {
        this.livrosCadastrados = livrosCadastrados;
    }

    public List<PropostaTroca> getPropostasEnviadas() {
        return propostasEnviadas;
    }

    public void setPropostasEnviadas(List<PropostaTroca> propostasEnviadas) {
        this.propostasEnviadas = propostasEnviadas;
    }

    public List<PropostaTroca> getPropostasRecebidas() {
        return propostasRecebidas;
    }

    public void setPropostasRecebidas(List<PropostaTroca> propostasRecebidas) {
        this.propostasRecebidas = propostasRecebidas;
    }

    public void addLivro(Livro livro) {
        if (this.livrosCadastrados == null) {
            this.livrosCadastrados = new ArrayList<>();
        }
        this.livrosCadastrados.add(livro);
        livro.setDono(this);
    }
}
