package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.edu.iff.ccc.books_trade.entities.Troca;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.entities.Livro;

import com.edu.iff.ccc.books_trade.dto.PropostaTrocaDTO;
import com.edu.iff.ccc.books_trade.service.LivroService;
import com.edu.iff.ccc.books_trade.service.UsuarioService;
import com.edu.iff.ccc.books_trade.service.PropostaTrocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;

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
    public String listarTrocas(Model model) {
        // Busca TODAS as propostas salvas no banco
        model.addAttribute("propostas", propostaService.findAll()); // Supondo que exista um método findAll()
        return "trocas"; // trocas.html
    }

    @GetMapping("/nova")
public String novaTrocaForm(Model model) {
    // 1. Cria o objeto principal para o formulário
    Troca troca = new Troca();
    
    // 2. Inicializa os objetos internos para evitar erro no th:field
    troca.setUsuario(new UsuarioComum());
    troca.setLivro(new Livro());     

    // 3. Adiciona o objeto ao model para o Thymeleaf poder usá-lo
    model.addAttribute("troca", troca);
    
    return "troca_form"; 
}

    @GetMapping("/{id}")
    public String detalhesTroca(@PathVariable("id") Long id, Model model) {
        model.addAttribute("trocaId", id);
        return "troca"; // troca.html
    }

    @GetMapping("/propor")
    public String proporTrocaForm(@RequestParam("livroDesejadoId") Long livroDesejadoId, Model model) {
        
        // Busca o livro desejado e seu dono (destinatário da proposta)
        Livro livroDesejado = livroService.findLivroById(livroDesejadoId);
        UsuarioComum destinatario = livroDesejado.getDono();

        // Busca todos os usuários (para o remetente selecionar quem ele é)
        List<UsuarioComum> todosUsuarios = usuarioService.findAllUsuariosComuns();
        // Busca todos os livros (para o remetente selecionar qual livro ofertar)
        List<Livro> todosLivros = livroService.findAllLivros();

        // Cria o DTO e já preenche os dados que sabemos
        PropostaTrocaDTO propostaDTO = new PropostaTrocaDTO();
        propostaDTO.setLivroDesejadoId(livroDesejado.getId());
        propostaDTO.setDestinatarioId(destinatario.getId());

        // Envia os dados para o template
        model.addAttribute("propostaDTO", propostaDTO);
        model.addAttribute("livroDesejado", livroDesejado);
        model.addAttribute("todosUsuarios", todosUsuarios);
        model.addAttribute("todosLivros", todosLivros);
        
        return "proposta_form"; // Renderiza o novo arquivo proposta_form.html
    }

    @PostMapping
    public String salvarProposta(@Valid @ModelAttribute("propostaDTO") PropostaTrocaDTO propostaDTO) {
        // A validação de erros pode ser adicionada aqui depois
        
        propostaService.criarProposta(
            propostaDTO.getLivroOfertadoId(),
            propostaDTO.getLivroDesejadoId(),
            propostaDTO.getRemetenteId(),
            propostaDTO.getDestinatarioId()
        );
        
        return "redirect:/trocas"; // Redireciona para a lista de trocas
    }
}

