package com.edu.iff.ccc.books_trade.service;

import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public void saveUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(idCounter++);
        }
        usuarios.add(usuario);
        System.out.println("Usuário salvo: " + usuario.getNome() + " - Email: " + usuario.getEmail());
    }

    public Optional<Usuario> findUsuarioById(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public List<UsuarioComum> findAllUsuariosComuns() {
        return usuarios.stream()
                .filter(u -> u instanceof UsuarioComum)
                .map(u -> (UsuarioComum) u)
                .collect(Collectors.toList());
    }

    public List<Usuario> findAllUsuarios() {
        if (usuarios.isEmpty()) {
            UsuarioComum usuario1 = new UsuarioComum("Joana Silva", "joana@example.com", "senha123", "9999-9999");
            usuario1.setId(idCounter++);
            usuarios.add(usuario1);

            UsuarioComum usuario2 = new UsuarioComum("Pedro Souza", "pedro@example.com", "outrasenha", "8888-8888");
            usuario2.setId(idCounter++);
            usuarios.add(usuario2);
        }
        return new ArrayList<>(usuarios);
    }
}