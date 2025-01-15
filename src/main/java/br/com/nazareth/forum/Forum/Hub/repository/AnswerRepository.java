package br.com.nazareth.forum.Forum.Hub.repository;

import br.com.nazareth.forum.Forum.Hub.entity.Resposta;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Resposta, Long> {

    Page<Resposta> findByTopicoId(Long topicoId, Pageable pageable);

    boolean existsByMensagemAndAutorAndTopico(String mensagem, Usuario autor, Topico topico);
}
