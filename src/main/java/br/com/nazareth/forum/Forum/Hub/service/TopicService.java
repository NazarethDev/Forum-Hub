package br.com.nazareth.forum.Forum.Hub.service;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.topics.DadosAtualizacao;
import br.com.nazareth.forum.Forum.Hub.model.topics.DadosNewTopic;
import br.com.nazareth.forum.Forum.Hub.model.topics.ShowTopicDetails;
import br.com.nazareth.forum.Forum.Hub.model.topics.TopicResponse;
import br.com.nazareth.forum.Forum.Hub.repository.CursoRepository;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
    Topico topic = new Topico(newTopic, autor, curso);
    return topicRepository.save(topic);
}


    private void verifyDuplication(DadosNewTopic newTopic){
        if (topicRepository.existsByTitulo(newTopic.titulo())){
            throw new IllegalArgumentException("Já existe um tópico com esse título!!");
        }
        if (topicRepository.existsByMensagem(newTopic.mensagem())){
            throw new IllegalArgumentException("Já existe um tópico com essa mensagem!!");
        }
    }

    private Curso getCurso(String nomeCurso) {
        return cursoRepository.findByNome(nomeCurso)
                .orElseGet(() -> {
                    Curso cursoVazio = new Curso();
                    cursoVazio.setNome("Nenhum curso do banco de dados selecionado.");
                    return cursoRepository.save(cursoVazio);  // Salva o curso no banco de dados
                });
    }

    @Transactional
    public Topico updateTopic(Long id, @Valid DadosAtualizacao dados) {
        var topico = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico com o ID especificado não encontrado."));

        var curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso com o ID especificado não encontrado."));

        topico.atualizar(dados, curso);
        return topico;
    }


    //Excluir tópico
    public void excludeTopic(Long id) {
        Topico topico = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado com o ID: " + id));

        topicRepository.delete(topico);
    }


    //listar todos os topicos
    public Page<TopicResponse> listarTopicos(Pageable paginacao) {
        Page<Topico> page = topicRepository.findAll(paginacao);

        // Convertendo os tópicos para o formato desejado (DTO)
        return page.map(topico -> new TopicResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getDataAtualicacao(),
                topico.isAnswered(),
                topico.getCurso().getNome(), // Retorna o nome do curso, por exemplo
                topico.getAutor().getNome()  // Retorna o nome do autor, sem a senha
        ));
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