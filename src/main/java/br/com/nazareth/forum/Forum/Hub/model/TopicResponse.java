package br.com.nazareth.forum.Forum.Hub.model;

import java.time.LocalDateTime;

public record TopicResponse(Long id,
                            String titulo,
                            String mensagem,
                            LocalDateTime dataCriacao,
                            LocalDateTime dataAtualicacao,
                            boolean answered,
                            String cursoNome,
                            String autorNome) {
}
