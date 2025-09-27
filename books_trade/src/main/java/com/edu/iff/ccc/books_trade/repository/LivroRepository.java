package com.edu.iff.ccc.books_trade.repository;

import com.edu.iff.ccc.books_trade.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // ANTES: findByDonoId(Long donoId);
    // AGORA: busca os livros de um dono QUE ESTÃO DISPONÍVEIS
    List<Livro> findByDonoIdAndDisponivelIsTrue(Long donoId);

    // ANTES: findByDonoIdNot(Long donoId);
    // AGORA: busca os livros de outros donos QUE ESTÃO DISPONÍVEIS
    List<Livro> findByDonoIdNotAndDisponivelIsTrue(Long donoId);
    
}
