package br.com.nazareth.forum.Forum.Hub.model.user;

public record DadosRespostaNovoUsuario(
        String nome,
        String email,
        Long id,
        String mensagem
) {
}
