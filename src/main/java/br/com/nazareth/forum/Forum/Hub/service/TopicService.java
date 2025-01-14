package br.com.nazareth.forum.Forum.Hub.service;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.DadosAtualizacao;
import br.com.nazareth.forum.Forum.Hub.model.DadosListagemTopicos;
import br.com.nazareth.forum.Forum.Hub.model.DadosNewTopic;
import br.com.nazareth.forum.Forum.Hub.model.ShowTopicDetails;
import br.com.nazareth.forum.Forum.Hub.repository.CursoRepository;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CursoRepository cursoRepository;

//cria um novo tópico
public Topico createNewTopic(DadosNewTopic newTopic, Usuario autor) {
        verifyDuplication(newTopic);
        Curso curso = getCurso(newTopic.curso());
        return new Topico(newTopic, autor, curso);
    }

    private void verifyDuplication(DadosNewTopic newTopic){
        if (topicRepository.existsByTitulo(newTopic.titulo())){
            throw new IllegalArgumentException("Já existe um tópico com esse título!!");
        }
        if (topicRepository.existsByMensagem(newTopic.mensagem())){
            throw new IllegalArgumentException("Já existe um tópico com essa mensagem!!");
        }
    }

    private Curso getCurso(String nomeCurso){
        return cursoRepository.findByNome(nomeCurso)
                .orElseGet(() -> {
                    Curso cursoVazio = new Curso();
                    cursoVazio.setNome("Nenhum curso selecionado");
                    return cursoVazio;
                });
    }


//    //atualizar tópico
//    public DadosAtualizacao ajustarDados(Long id, DadosAtualizacao dados) {
//        return new DadosAtualizacao(dados.titulo(), dados.mensagem(), dados.curso());
//}

//    public void updateTopic(Long id, @Valid DadosAtualizacao dados) {
//        var dadosAjustados = ajustarDados(id, dados);
//
//        var topicoOptional = topicRepository.findById(id);
//
//        if (topicoOptional.isEmpty()) {
//            throw new IllegalArgumentException("Tópico com o ID especificado não encontrado.");
//        }
//
//        var topico = topicoOptional.get();
//
//        topico.atualizar(dadosAjustados);
//
//        topicRepository.save(topico);
//    }

    //Excluir tópico
    public void excludeTopic(DadosListagemTopicos dados) {

        boolean exists = topicRepository.existsById(dados.id());

        if (!exists) {
            throw new IllegalArgumentException("Não foi encontrado um tópico com esse ID :(");
        }

        topicRepository.deleteById(dados.id());
    }

    //listar todos os topicos
    public ResponseEntity <Page<DadosListagemTopicos>>listarTopicos (@PageableDefault(size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.ASC)Pageable paginacao){
        var page = topicRepository.findAll(paginacao)
                .map(DadosListagemTopicos::new);
        return ResponseEntity.ok(page);
    }

    //apresentar topico em especifico
    public ResponseEntity mostrarTopico(@PathVariable Long id){
        var topic = topicRepository.findById(id);
        if (topic.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
        }
        return ResponseEntity.ok(new ShowTopicDetails(topic.get()));
    }

}