package com.edu.iff.ccc.books_trade.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trocas")
public class Troca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id") // O nome da coluna pode variar
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id") // O nome da coluna pode variar
    private Livro livro;

    // Relacionamento One-to-One: cada troca se refere a uma proposta de troca.
    @OneToOne
    @JoinColumn(name = "proposta_troca_id")
    private PropostaTroca propostaTroca;

    // Relacionamento Many-to-One: cada troca tem um livro enviado.
    @ManyToOne
    @JoinColumn(name = "livro_enviado_id")
    private Livro livroEnviado;

    // Relacionamento Many-to-One: cada troca tem um livro recebido.
    @ManyToOne
    @JoinColumn(name = "livro_recebido_id")
    private Livro livroRecebido;

    // Relacionamento Many-to-One: cada troca tem um usuário que a originou.
    @ManyToOne
    @JoinColumn(name = "usuario_origem_id")
    private UsuarioComum usuarioOrigem;

    // Relacionamento Many-to-One: cada troca tem um usuário que a recebeu.
    @ManyToOne
    @JoinColumn(name = "usuario_destino_id")
    private UsuarioComum usuarioDestino;

    @Temporal(TemporalType.DATE)
    private Date dataTroca;

    // Construtores
    public Troca() {
    }

    // Getters e Setters (omiti para encurtar, mas você precisa adicioná-los)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
    return usuario;
    }

    public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
    }

    public Livro getLivro() {
    return livro;
    }

    public void setLivro(Livro livro) {
    this.livro = livro;
    }


    public PropostaTroca getPropostaTroca() {
        return propostaTroca;
    }

    public void setPropostaTroca(PropostaTroca propostaTroca) {
        this.propostaTroca = propostaTroca;
    }

    public Livro getLivroEnviado() {
        return livroEnviado;
    }

    public void setLivroEnviado(Livro livroEnviado) {
        this.livroEnviado = livroEnviado;
    }

    public Livro getLivroRecebido() {
        return livroRecebido;
    }

    public void setLivroRecebido(Livro livroRecebido) {
        this.livroRecebido = livroRecebido;
    }

    public UsuarioComum getUsuarioOrigem() {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem(UsuarioComum usuarioOrigem) {
        this.usuarioOrigem = usuarioOrigem;
    }

    public UsuarioComum getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(UsuarioComum usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Date getDataTroca() {
        return dataTroca;
    }

    public void setDataTroca(Date dataTroca) {
        this.dataTroca = dataTroca;
    }
}