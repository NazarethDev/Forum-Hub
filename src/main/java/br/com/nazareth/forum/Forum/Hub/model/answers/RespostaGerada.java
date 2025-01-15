package br.com.nazareth.forum.Forum.Hub.model.answers;

import br.com.nazareth.forum.Forum.Hub.entity.Resposta;
import java.time.LocalDateTime;

public record RespostaGerada(Long id,
                             String mensagem,
                             LocalDateTime dataCriacao,
                             String autor) {
    public RespostaGerada(Resposta resposta){
        this(resposta.getId(), resposta.getMensagem(),resposta.getDataCriacao(),resposta.getAutor().getNome());
    }
}
