package br.com.nazareth.forum.Forum.Hub.service;

import br.com.nazareth.forum.Forum.Hub.entity.Resposta;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.answers.NewAnswerDates;
import br.com.nazareth.forum.Forum.Hub.model.answers.RespostaGerada;
import br.com.nazareth.forum.Forum.Hub.model.answers.RespostasListagem;
import br.com.nazareth.forum.Forum.Hub.repository.AnswerRepository;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    public ResponseEntity novaResposta(NewAnswerDates dados, Usuario autor) {
        var topico = topicRepository.findById(dados.topicId())
                .orElseThrow(()->new EntityNotFoundException("Tópico não encontrado :("));
        boolean respostaExiste = answerRepository.existsByMensagemAndAutorAndTopico(dados.mensagem(), autor, topico);

        if (respostaExiste) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe uma resposta semelhante para este tópico e autor.");
        }
        var resposta = new Resposta(dados, autor, topico);
        topico.getRespostas().add(resposta);

        if (!topico.isAnswered()){
            topico.setAnswered(true);
            topicRepository.save(topico);
        }

        answerRepository.save(resposta);
        return ResponseEntity.ok(new RespostaGerada(resposta));
    }

    public Page<RespostasListagem> listarRespostasPorTopico(Long topicoId, Pageable paginacao) {
        Page<Resposta> respostas = answerRepository.findByTopicoId(topicoId, paginacao);
        return respostas.map(resposta -> new RespostasListagem(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao()));
    }
}