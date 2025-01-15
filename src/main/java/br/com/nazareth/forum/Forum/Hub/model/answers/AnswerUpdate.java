package br.com.nazareth.forum.Forum.Hub.model.answers;

import jakarta.validation.constraints.NotNull;


public record AnswerUpdate(
        @NotNull
        String mensagem
) {
}
