package br.com.nazareth.forum.Forum.Hub.model;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;

public record DadosCursosEmDB(Long id,
                              String nome,
                              Categoria categoria
) {
    public static DadosCursosEmDB fromEntity(Curso curso) {
        return new DadosCursosEmDB(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}