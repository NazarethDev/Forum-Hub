package br.com.nazareth.forum.Forum.Hub.model;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import jakarta.validation.constraints.NotBlank;

public record DadosNewTopic(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        Usuario autor,

        String curso) {
}