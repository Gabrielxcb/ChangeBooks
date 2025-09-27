package com.edu.iff.ccc.books_trade.controller.view;

import com.edu.iff.ccc.books_trade.dto.PropostaTrocaDTO;
import com.edu.iff.ccc.books_trade.entities.Livro;
import com.edu.iff.ccc.books_trade.entities.PropostaTroca;
import com.edu.iff.ccc.books_trade.entities.Usuario;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.service.LivroService;
import com.edu.iff.ccc.books_trade.service.PropostaTrocaService;
import com.edu.iff.ccc.books_trade.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/trocas")
public class TrocaViewController {

    private final PropostaTrocaService propostaService;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    @Autowired
    public TrocaViewController(PropostaTrocaService propostaService, LivroService livroService, UsuarioService usuarioService) {
        this.propostaService = propostaService;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarTrocas(Model model, Principal principal) {
        // 1. Identifica o usuário logado
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    
        // 2. Busca no serviço apenas as propostas relacionadas a este usuário
        List<PropostaTroca> minhasPropostas = propostaService.findPropostasByUsuarioId(usuarioLogado.getId());
    
        // 3. Envia a lista filtrada para o template
        model.addAttribute("propostas", minhasPropostas);
        model.addAttribute("usuarioLogadoId", usuarioLogado.getId()); // Enviando o ID do usuário logado
    
        return "trocas";
    }

    @GetMapping("/propor")
    public String proporTrocaForm(@RequestParam("livroDesejadoId") Long livroDesejadoId, Model model, Principal principal) {

        // 1. Busca o usuário logado (remetente). Graças ao FetchType.EAGER, a lista de livros dele já vem junto.
        UsuarioComum remetente = (UsuarioComum) usuarioService.findUsuarioByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("Remetente não encontrado."));

        // 2. Busca o livro desejado
        Livro livroDesejado = livroService.findLivroById(livroDesejadoId);

        // 3. Pega a lista de livros DIRETAMENTE do objeto remetente.
        List<Livro> livrosOfertados = remetente.getLivrosCadastrados();

        // --- PASSO DE DEBUG PARA TER CERTEZA ---
        // Adicione esta linha temporariamente para ver no console o que está acontecendo.
        System.out.println("DEBUG: Encontrados " + (livrosOfertados != null ? livrosOfertados.size() : 0) + " livros para o usuário " + remetente.getNome());
        // -----------------------------------------

        // 4. Preenche o DTO e envia os dados para o template
        PropostaTrocaDTO propostaDTO = new PropostaTrocaDTO();
        propostaDTO.setLivroDesejadoId(livroDesejado.getId());
        propostaDTO.setDestinatarioId(livroDesejado.getDono().getId());
        propostaDTO.setRemetenteId(remetente.getId());

        model.addAttribute("propostaDTO", propostaDTO);
        model.addAttribute("livroDesejado", livroDesejado);
        model.addAttribute("remetente", remetente);
        model.addAttribute("livrosOfertados", livrosOfertados);
    
        return "proposta_form";
    }

    @PostMapping
    public String salvarProposta(@Valid @ModelAttribute("propostaDTO") PropostaTrocaDTO propostaDTO) {
        propostaService.criarProposta(
            propostaDTO.getLivroOfertadoId(),
            propostaDTO.getLivroDesejadoId(),
            propostaDTO.getRemetenteId(),
            propostaDTO.getDestinatarioId()
        );
        
        return "redirect:/trocas";
    }

    // Futuramente, adicionaremos métodos para aceitar/recusar propostas aqui
}

