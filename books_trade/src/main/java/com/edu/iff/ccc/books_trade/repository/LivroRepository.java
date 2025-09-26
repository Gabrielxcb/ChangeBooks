package com.edu.iff.ccc.books_trade.repository;

import com.edu.iff.ccc.books_trade.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    // O Spring Data JPA já fornece métodos como findAll(), findById(), save(), etc.
    // Futuramente, você poderá adicionar consultas customizadas aqui.
}
