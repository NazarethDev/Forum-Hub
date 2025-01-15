package br.com.nazareth.forum.Forum.Hub.model.answers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewAnswerDates(
        @NotNull
        Long topicId,
        @NotBlank
        String mensagem
) {
}
