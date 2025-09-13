package com.edu.iff.ccc.books_trade.repository;

import br.edu.iff.ccc.webdev.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
    Livro findByTitulo(String titulo);

    List<Livro> findLivroById(int id);

}