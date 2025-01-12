package br.com.nazareth.forum.Forum.Hub.model;

public record DadosRespostaNovoUsuario(
        String nome,
        String email,
        Long id,
        String mensagem
) {
}
