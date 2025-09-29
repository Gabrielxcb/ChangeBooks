package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.dto.LivroDTO;
import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.exceptions.LivroNaoEncontradoException;
import com.edu.iff.ccc.books_trade.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Livro saveLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro findLivroById(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com o ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Livro> findAllLivros() {
        return livroRepository.findAll();
    }
    
    @Transactional
    public void deleteLivroById(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNaoEncontradoException("Livro não encontrado com o ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Livro> findLivrosByDonoId(Long donoId) {
        return livroRepository.findByDonoIdAndDisponivelIsTrue(donoId);
    }

    @Transactional(readOnly = true)
    public List<Livro> findLivrosDisponiveis(Long meuId) {
        return livroRepository.findByDonoIdNotAndDisponivelIsTrue(meuId);
    }

    @Transactional
    public Livro updateLivro(Long livroId, LivroDTO livroDTO, Usuario dono) {
        Livro livro = this.findLivroById(livroId);

        if (!livro.getDono().getId().equals(dono.getId())) {
            throw new SecurityException("Você não tem permissão para editar este livro.");
        }

        livro.setAutor(livroDTO.getAutor());
        livro.setGenero(livroDTO.getGenero());
        livro.setEstadoConservacao(livroDTO.getEstadoConservacao());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
    
        return livroRepository.save(livro);
    }

    @Transactional
    public void deleteLivro(Long livroId, Usuario usuarioLogado) {
        Livro livro = this.findLivroById(livroId);

        if (!livro.getDono().getId().equals(usuarioLogado.getId())) {
        throw new SecurityException("Você não tem permissão para excluir este livro.");
        }
    
        UsuarioComum dono = livro.getDono();
    
        dono.getLivrosCadastrados().remove(livro);
    
        livro.setDono(null);
    
        livroRepository.delete(livro);
    }
}