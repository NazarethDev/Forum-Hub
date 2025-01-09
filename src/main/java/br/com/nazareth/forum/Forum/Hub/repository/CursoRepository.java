package br.com.nazareth.forum.Forum.Hub.repository;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long > {

  Optional<Curso> findByNome(String nome);

  boolean existsByNomeAndCategoria(String nome, Categoria categoria);
}