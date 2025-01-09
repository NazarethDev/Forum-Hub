package br.com.nazareth.forum.Forum.Hub.repository;

import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository <Topico, Long> {

    boolean existsByTitulo(String titulo);

    boolean existsByMensagem(String mensagem);
}
