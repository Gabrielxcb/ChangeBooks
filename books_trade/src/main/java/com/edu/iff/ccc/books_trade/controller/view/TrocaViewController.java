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
        Usuario usuarioLogado = usuarioService.findUsuarioByEmail(principal.getName());
        List<PropostaTroca> minhasPropostas = propostaService.findPropostasByUsuarioId(usuarioLogado.getId());
        model.addAttribute("propostas", minhasPropostas);
        model.addAttribute("usuarioLogadoId", usuarioLogado.getId());
        return "trocas";
    }

    @GetMapping("/propor")
    public String proporTrocaForm(@RequestParam("livroDesejadoId") Long livroDesejadoId, Model model, Principal principal) {
        UsuarioComum remetente = (UsuarioComum) usuarioService.findUsuarioByEmail(principal.getName());
        Livro livroDesejado = livroService.findLivroById(livroDesejadoId);

        List<Livro> livrosOfertados = livroService.findLivrosByDonoId(remetente.getId());

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

    @PostMapping("/{id}/aceitar")
    public String aceitarProposta(@PathVariable("id") Long propostaId) {
        propostaService.aceitarProposta(propostaId);
        return "redirect:/trocas";
    }

    @PostMapping("/{id}/recusar")
    public String recusarProposta(@PathVariable("id") Long propostaId) {
        propostaService.recusarProposta(propostaId);
        return "redirect:/trocas";
    }

    @GetMapping("/{id}")
    public String detalhesTroca(@PathVariable("id") Long id, Model model) {
        model.addAttribute("trocaId", id);
        return "troca"; 
    }
}

