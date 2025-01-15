package br.com.nazareth.forum.Forum.Hub.repository;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    UserDetails findByNome(String nome);

    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome")
    Optional<Usuario> findUsuarioByNome(@Param("nome") String nome);

}