package br.com.nazareth.forum.Forum.Hub.model.user;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank
        String nome,
        @NotBlank
        String senha
) {
}
