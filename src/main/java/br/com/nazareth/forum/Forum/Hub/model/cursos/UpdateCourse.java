package br.com.nazareth.forum.Forum.Hub.model.cursos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCourse(
        @NotBlank
        String nome,
        @NotNull
        Categoria categoria
) {
}
