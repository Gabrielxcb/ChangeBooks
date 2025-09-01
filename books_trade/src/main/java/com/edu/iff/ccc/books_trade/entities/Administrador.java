package com.edu.iff.ccc.books_trade.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADM")
public class Administrador extends Usuario {

    // Construtores
    public Administrador() {
        super();
    }

    public Administrador(String nome, String email, String senha, String telefone) {
        super(nome, email, senha, telefone);
    }
}