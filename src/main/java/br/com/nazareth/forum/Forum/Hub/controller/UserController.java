package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.model.answers.RespostasPorUsuario;
import br.com.nazareth.forum.Forum.Hub.model.topics.ShowTopicDetails;
import br.com.nazareth.forum.Forum.Hub.model.user.DadosAutenticacao;
import br.com.nazareth.forum.Forum.Hub.model.user.DadosNovoUsuario;
import br.com.nazareth.forum.Forum.Hub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados){
        return usuarioService.entrar(dados);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar (@RequestBody @Valid DadosNovoUsuario dados){
        return usuarioService.createUser(dados);
    }

    @GetMapping("/{usuarioId}/topicos")
    public ResponseEntity<Page<ShowTopicDetails>> getTopicosByUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable) {

        Page<ShowTopicDetails> topicos = usuarioService.getTopicosByUsuario(usuarioId, pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{usuarioId}/respostas")
    public ResponseEntity<Page<RespostasPorUsuario>> getRespostasByUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable) {

        Page<RespostasPorUsuario> respostas = usuarioService.getRespostasByUsuario(usuarioId, pageable);
        return ResponseEntity.ok(respostas);
    }

}