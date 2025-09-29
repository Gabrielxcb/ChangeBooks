package com.edu.iff.ccc.books_trade.repository;

import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para encontrar um usuário pelo email, útil para o login
    Optional<Usuario> findByEmail(String email);

    // Query para buscar apenas os usuários do tipo COMUM
    @Query("SELECT u FROM Usuario u WHERE TYPE(u) = UsuarioComum")
    List<UsuarioComum> findAllUsuariosComuns();
}
