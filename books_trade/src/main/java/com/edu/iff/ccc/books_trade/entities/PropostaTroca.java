package com.edu.iff.ccc.books_trade.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "propostas_troca")
public class PropostaTroca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_ofertado_id")
    private Livro livroOfertado;

    @ManyToOne
    @JoinColumn(name = "livro_desejado_id")
    private Livro livroDesejado;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Livro getLivroOfertado() {
		return livroOfertado;
	}

	public void setLivroOfertado(Livro livroOfertado) {
		this.livroOfertado = livroOfertado;
	}

	public Livro getLivroDesejado() {
		return livroDesejado;
	}

	public void setLivroDesejado(Livro livroDesejado) {
		this.livroDesejado = livroDesejado;
	}

	public UsuarioComum getRemetente() {
		return remetente;
	}

	public void setRemetente(UsuarioComum remetente) {
		this.remetente = remetente;
	}

	public UsuarioComum getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioComum destinatario) {
		this.destinatario = destinatario;
	}

	public StatusProposta getStatus() {
		return status;
	}

	public void setStatus(StatusProposta status) {
		this.status = status;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne
    @JoinColumn(name = "remetente_id")
    private UsuarioComum remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private UsuarioComum destinatario;

    @Enumerated(EnumType.STRING)
    private StatusProposta status; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;

    
    public PropostaTroca() {
    }

}
