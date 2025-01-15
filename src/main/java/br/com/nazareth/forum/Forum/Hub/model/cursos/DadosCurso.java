package br.com.nazareth.forum.Forum.Hub.model.cursos;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCurso(
        @NotBlank
        String nome,
        @NotNull
        Categoria categoria
) {
        public static DadosCurso fromEntity(Curso curso) {
                return new DadosCurso(curso.getNome(), curso.getCategoria());
        }
}