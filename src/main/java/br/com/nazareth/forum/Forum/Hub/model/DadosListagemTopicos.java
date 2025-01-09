package br.com.nazareth.forum.Forum.Hub.model;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;

import java.time.LocalDateTime;

public record DadosListagemTopicos(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        boolean answered,
        Curso curso,
        Usuario autor
) {
    public DadosListagemTopicos(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.isAnswered(), topico.getCurso(),topico.getAutor());
    }
}
