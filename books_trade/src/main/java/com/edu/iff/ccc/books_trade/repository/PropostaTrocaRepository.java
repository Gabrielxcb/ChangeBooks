package com.edu.iff.ccc.books_trade.repository;

import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropostaTrocaRepository extends JpaRepository<PropostaTroca, Long> {

    // Busca propostas onde o usuário é o remetente OU o destinatário
    List<PropostaTroca> findByRemetenteIdOrDestinatarioId(Long remetenteId, Long destinatarioId);
}
