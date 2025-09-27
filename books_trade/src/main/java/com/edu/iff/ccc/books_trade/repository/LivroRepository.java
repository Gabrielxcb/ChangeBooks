package com.edu.iff.ccc.books_trade.repository;

import com.edu.iff.ccc.books_trade.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByDonoId(Long donoId);

    List<Livro> findByDonoIdNot(Long donoId);
}
