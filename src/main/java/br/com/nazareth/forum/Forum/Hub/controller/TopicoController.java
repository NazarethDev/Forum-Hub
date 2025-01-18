package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.topics.DadosAtualizacao;
import br.com.nazareth.forum.Forum.Hub.model.topics.DadosNewTopic;
import br.com.nazareth.forum.Forum.Hub.model.topics.DadosTopicoAtualizado;
import br.com.nazareth.forum.Forum.Hub.model.topics.TopicResponse;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import br.com.nazareth.forum.Forum.Hub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity newTopic(@RequestBody @Valid DadosNewTopic newTopic,
                                   UriComponentsBuilder uriBuilder,
                                   @AuthenticationPrincipal Usuario usuarioLogado) {
        var topic = topicService.createNewTopic(newTopic, usuarioLogado); // Não precisa mais salvar no controller
        var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body("Tópico criado com sucesso!");
    }


    @GetMapping
    public ResponseEntity<Page<TopicResponse>> showTopics(
            @PageableDefault(size = 3, sort = {"dataCriacao"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        Page<TopicResponse> topicos = topicService.listarTopicos(paginacao);
        return ResponseEntity.ok(topicos);
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
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        topicService.excludeTopic(id);
        return ResponseEntity.ok("Tópico excluído com sucesso!");
    }
}