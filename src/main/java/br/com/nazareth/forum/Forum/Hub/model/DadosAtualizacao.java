package br.com.nazareth.forum.Forum.Hub.model;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAtualizacao (
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Long id,
        @NotNull
        LocalDateTime dataAtualizacao,
        boolean answered,
        @NotNull
        Curso curso,
        @NotNull
        Usuario autor
) {
    public DadosAtualizacao(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getId(), topico.getDataAtualicacao(), topico.isAnswered(), topico.getCurso(),topico.getAutor());
    }

}
