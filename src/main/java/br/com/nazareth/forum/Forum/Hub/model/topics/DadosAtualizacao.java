package br.com.nazareth.forum.Forum.Hub.model.topics;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacao(
        @NotBlank
        String titulo,
        @NotNull
        String mensagem,
        @NotNull
        Long cursoId
) {
}
