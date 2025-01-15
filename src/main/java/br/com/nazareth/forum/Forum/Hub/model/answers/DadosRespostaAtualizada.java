package br.com.nazareth.forum.Forum.Hub.model.answers;

import br.com.nazareth.forum.Forum.Hub.entity.Resposta;

import java.time.LocalDateTime;

public record DadosRespostaAtualizada(
        Long id,
        String mensagem,
        LocalDateTime dataAtualizacao
) {
    public DadosRespostaAtualizada(Resposta resposta){
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataAtualizacao());
    }
}
