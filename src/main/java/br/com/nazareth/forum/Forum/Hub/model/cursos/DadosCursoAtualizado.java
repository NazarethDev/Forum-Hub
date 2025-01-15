package br.com.nazareth.forum.Forum.Hub.model.cursos;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;

public record DadosCursoAtualizado(
        Long id,
        String nome,
        Categoria categoria
) {
    public DadosCursoAtualizado(Curso curso){
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
