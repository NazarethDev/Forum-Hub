package br.com.nazareth.forum.Forum.Hub.infra.service;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.model.DadosAtualizacao;
import br.com.nazareth.forum.Forum.Hub.model.DadosListagemTopicos;
import br.com.nazareth.forum.Forum.Hub.model.DadosNewTopic;
import br.com.nazareth.forum.Forum.Hub.repository.CursoRepository;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CursoRepository cursoRepository;

//cria um novo tópico
    public Topico createNewTopic(DadosNewTopic newTopic){
        verifyDuplication(newTopic);
        Curso curso = getCurso(newTopic.curso());
        return makeNewTopic(newTopic,curso);
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

    private Topico makeNewTopic(DadosNewTopic newTopic, Curso curso){
        Topico topico = new Topico();
        topico.setTitulo(newTopic.titulo());
        topico.setMensagem(newTopic.mensagem());
        topico.setAutor(newTopic.autor());
        topico.setDataCriacao(LocalDateTime.now());
        topico.setCurso(curso);
        topico.setAnswered(false);

        return topico;
    }

//atualizar tópico
    public DadosAtualizacao ajustarDados(Long id, DadosAtualizacao dados) {
        return new DadosAtualizacao(
                dados.titulo(),
                dados.mensagem(),
                id,
                dados.dataAtualizacao(),
                dados.answered(),
                dados.curso(),
                dados.autor()
        );
    }

    public void updateTopic(Long id, @Valid DadosAtualizacao dados) {
        var dadosAjustados = ajustarDados(id, dados);

        var topicoOptional = topicRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            throw new IllegalArgumentException("Tópico com o ID especificado não encontrado.");
        }

        var topico = topicoOptional.get();

        topico.atualizar(dadosAjustados);

        topicRepository.save(topico);
    }

    //Excluir tópico
    public void excludeTopic(DadosListagemTopicos dados) {

        boolean exists = topicRepository.existsById(dados.id());

        if (!exists) {
            throw new IllegalArgumentException("Não foi encontrado um tópico com esse ID :(");
        }

        topicRepository.deleteById(dados.id());
    }

}