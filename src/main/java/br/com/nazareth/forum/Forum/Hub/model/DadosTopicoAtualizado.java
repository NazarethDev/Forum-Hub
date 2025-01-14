package br.com.nazareth.forum.Forum.Hub.model;

import br.com.nazareth.forum.Forum.Hub.entity.Topico;

import java.time.LocalDateTime;

public record DadosTopicoAtualizado(Long id,
                                    String titulo,
                                    String mensagem,
                                    String curso,
                                    String autor,
                                    LocalDateTime dataAtualicacao
) {
    public DadosTopicoAtualizado(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getCurso().getNome(),
                topico.getAutor().getNome(), // Caso tenha um atributo `nome` no autor
                topico.getDataAtualicacao()
        );
    }
}