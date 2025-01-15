package br.com.nazareth.forum.Forum.Hub.repository;

import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository <Topico, Long> {

    boolean existsByTitulo(String titulo);

    boolean existsByMensagem(String mensagem);

    Optional<Topico> findById (Long id);

    Page<Topico> findByAutor(Usuario usuario, Pageable pageable);
}