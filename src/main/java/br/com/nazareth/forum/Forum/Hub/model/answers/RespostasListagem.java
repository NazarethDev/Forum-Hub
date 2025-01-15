package br.com.nazareth.forum.Forum.Hub.model.answers;

import java.time.LocalDateTime;

public record RespostasListagem(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao
) {
    public RespostasListagem(Long id, String mensagem, LocalDateTime dataCriacao) {
        this.id = id;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
    }
}
