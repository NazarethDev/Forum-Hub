package br.com.nazareth.forum.Forum.Hub.repository;

import br.com.nazareth.forum.Forum.Hub.entity.Resposta;
import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Resposta, Long> {

    Page<Resposta> findByTopicoId(Long topicoId, Pageable pageable);

    boolean existsByMensagemAndAutorAndTopico(String mensagem, Usuario autor, Topico topico);

    List<Resposta> findByAutor(Usuario autor);

    @Query("SELECT r FROM Resposta r WHERE r.autor = :autor")
    Page<Resposta> findRespostasByAutor(@Param("autor") Usuario autor, Pageable pageable);


}
