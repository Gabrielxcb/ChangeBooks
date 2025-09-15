package com.edu.iff.ccc.books_trade.repository;

import com.edu.iff.ccc.books_trade.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNome(String nome);
    
    List<Usuario> findByEmail(String email);
}