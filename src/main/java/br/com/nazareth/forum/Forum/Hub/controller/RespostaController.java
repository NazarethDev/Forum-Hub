package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.answers.NewAnswerDates;
import br.com.nazareth.forum.Forum.Hub.model.answers.RespostasListagem;
import br.com.nazareth.forum.Forum.Hub.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class RespostaController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public ResponseEntity newAnswer(@RequestBody @Valid NewAnswerDates dados, @AuthenticationPrincipal Usuario autor){
        return answerService.novaResposta(dados, autor);
    }

    @GetMapping("/{topicoId}")
    public ResponseEntity<Page<RespostasListagem>> listarRespostasPorTopico(
            @PathVariable Long topicoId,
            @PageableDefault(size = 3) Pageable paginacao) {
        Page<RespostasListagem> respostas = answerService.listarRespostasPorTopico(topicoId, paginacao);
        return ResponseEntity.ok(respostas);
    }

}
