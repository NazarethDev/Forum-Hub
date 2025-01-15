package br.com.nazareth.forum.Forum.Hub.model.topics;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record DadosListagemTopicos(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Long id,
        @NotNull
        LocalDateTime dataCriacao,
        boolean answered,
        @NotNull
        Curso curso,
        @NotNull
        Usuario autor
) {
    public DadosListagemTopicos(Topico topico) {
        this(topico.getTitulo(), topico.getMensagem(), topico.getId(), topico.getDataCriacao(), topico.isAnswered(), topico.getCurso(), topico.getAutor());
    }
}
