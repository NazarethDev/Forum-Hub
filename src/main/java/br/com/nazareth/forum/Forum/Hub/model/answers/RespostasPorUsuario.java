package br.com.nazareth.forum.Forum.Hub.model.answers;

import br.com.nazareth.forum.Forum.Hub.entity.Resposta;

import java.time.LocalDateTime;

public record RespostasPorUsuario(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        Long topicoId
) {
    public RespostasPorUsuario (Resposta resposta){
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(),resposta.getDataAtualizacao(), resposta.getTopico().getId());
    }
}
