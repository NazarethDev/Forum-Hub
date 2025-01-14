package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.DadosAtualizacao;
import br.com.nazareth.forum.Forum.Hub.model.DadosListagemTopicos;
import br.com.nazareth.forum.Forum.Hub.model.DadosNewTopic;
import br.com.nazareth.forum.Forum.Hub.model.DadosTopicoAtualizado;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import br.com.nazareth.forum.Forum.Hub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topico")
public class TopicoController {

    @Autowired
    TopicService topicService;
    @Autowired
    TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> newTopic(@RequestBody @Valid DadosNewTopic newTopic,
                                      UriComponentsBuilder uriBuilder,
                                      @AuthenticationPrincipal Usuario usuarioLogado) {
        var topic = topicService.createNewTopic(newTopic, usuarioLogado);
        topicRepository.save(topic);
        var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body("Tópico criado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> showTopics (Pageable paginacao){
        return topicService.listarTopicos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity showTopicDetails(@PathVariable Long id){
        return topicService.mostrarTopico(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosTopicoAtualizado> update(@PathVariable Long id, @RequestBody @Valid DadosAtualizacao dados) {
        var topicoAtualizado = topicService.updateTopic(id, dados);
        return ResponseEntity.ok(new DadosTopicoAtualizado(topicoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTopic(@PathVariable Long id, DadosListagemTopicos dados){
        topicService.excludeTopic(dados);
        return ResponseEntity.noContent().build();
    }

}