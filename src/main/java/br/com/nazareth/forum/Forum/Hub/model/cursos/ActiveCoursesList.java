package br.com.nazareth.forum.Forum.Hub.model.cursos;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;

public record ActiveCoursesList(Long id,
                              String nome,
                              Categoria categoria
) {
    public static ActiveCoursesList fromEntity(Curso curso) {
        return new ActiveCoursesList(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
