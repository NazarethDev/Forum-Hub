package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.model.DadosAtualizacao;
import br.com.nazareth.forum.Forum.Hub.model.DadosListagemTopicos;
import br.com.nazareth.forum.Forum.Hub.model.DadosNewTopic;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import br.com.nazareth.forum.Forum.Hub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity newTopic(@RequestBody @Valid DadosNewTopic newTopic, UriComponentsBuilder uriBuilder){
        var topic = topicService.createNewTopic(newTopic);
        topicRepository.save(topic);
        var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body("Tópico criado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> showTopics (@PageableDefault(size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.ASC)Pageable paginacao){
        var page = topicRepository.findAll(paginacao)
                .map(DadosListagemTopicos::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity showTopicDetails(@PathVariable Long id){
        var topic = topicRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosListagemTopicos(topic));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateTopic(@PathVariable Long id, @RequestBody @Valid DadosAtualizacao dados) {
        topicService.updateTopic(id, dados);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTopic(@PathVariable Long id, DadosListagemTopicos dados){
        topicService.excludeTopic(dados);
        return ResponseEntity.noContent().build();
    }

}