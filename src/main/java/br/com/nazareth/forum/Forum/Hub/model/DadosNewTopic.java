package br.com.nazareth.forum.Forum.Hub.model;

import jakarta.validation.constraints.NotBlank;

public record DadosNewTopic(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        String curso) {
}