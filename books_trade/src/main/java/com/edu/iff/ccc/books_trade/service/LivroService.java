package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.dto.LivroDTO;
import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
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
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Livro> findAllLivros() {
        return livroRepository.findAll();
    }
    
    @Transactional
    public void deleteLivroById(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado com o ID: " + id);
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
        // 1. Busca o livro original no banco
        Livro livro = this.findLivroById(livroId);

        // 2. VERIFICAÇÃO DE SEGURANÇA: Garante que o usuário logado é o dono do livro
        if (!livro.getDono().getId().equals(dono.getId())) {
            throw new SecurityException("Você não tem permissão para editar este livro.");
        }

        // 3. Atualiza os campos do livro com os dados do DTO
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setGenero(livroDTO.getGenero());
        livro.setEstadoConservacao(livroDTO.getEstadoConservacao());
        livro.setDescricao(livroDTO.getDescricao());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
    
        // 4. Salva o livro atualizado
        return livroRepository.save(livro);
    }

    @Transactional
    public void deleteLivro(Long livroId, Usuario usuarioLogado) {
        // 1. Busca o livro que será excluído
        Livro livro = this.findLivroById(livroId);

        // 2. VERIFICAÇÃO DE SEGURANÇA: Garante que o usuário logado é o dono do livro
        if (!livro.getDono().getId().equals(usuarioLogado.getId())) {
        throw new SecurityException("Você não tem permissão para excluir este livro.");
        }
    
        // --- LÓGICA CORRETA PARA EXCLUSÃO BIDIRECIONAL ---
    
        // 3. Pega a referência do "pai" (o dono do livro)
        UsuarioComum dono = livro.getDono();
    
        // 4. Remove o "filho" (livro) da coleção do "pai"
        // Esta é a etapa mais importante para quebrar a relação.
        dono.getLivrosCadastrados().remove(livro);
    
        // 5. (Opcional, mas boa prática) Remove a referência do "pai" no "filho"
        livro.setDono(null);
    
        // 6. Agora que os laços foram cortados, podemos deletar o livro com segurança.
        livroRepository.delete(livro);
}
}