package br.com.nazareth.forum.Forum.Hub.model;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosNewTopic(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Usuario autor,
        @NotBlank
        String curso) {
}