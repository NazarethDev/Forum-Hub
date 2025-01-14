package br.com.nazareth.forum.Forum.Hub.model;

import br.com.nazareth.forum.Forum.Hub.entity.Topico;

import java.time.LocalDateTime;

public record ShowTopicDetails(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        boolean answered,
        String cursoNome,
        String autorNome,
        String autorEmail
) {
    public ShowTopicDetails(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.isAnswered(),
                topico.getCurso().getNome(),
                topico.getAutor().getNome(),
                topico.getAutor().getEmail()
        );
    }
}

