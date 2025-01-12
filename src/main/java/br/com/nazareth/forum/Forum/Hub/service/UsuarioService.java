package br.com.nazareth.forum.Forum.Hub.service;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.DadosAutenticacao;
import br.com.nazareth.forum.Forum.Hub.model.DadosNovoUsuario;
import br.com.nazareth.forum.Forum.Hub.model.DadosRespostaNovoUsuario;
import br.com.nazareth.forum.Forum.Hub.repository.UsuarioRepository;
import br.com.nazareth.forum.Forum.Hub.security.JWTTokenDates;
import br.com.nazareth.forum.Forum.Hub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity createUser(@Valid DadosNovoUsuario dados) {
        if (usuarioRepository.findUsuarioByNome(dados.nome()).isPresent()){
            return ResponseEntity.badRequest().body("Usuário já existe!");
        }
        var usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new DadosRespostaNovoUsuario(usuario.getNome(),
                        usuario.getEmail(), usuario.getId(), "Usuário cadastrado com sucesso! :)"));
    }

    public ResponseEntity entrar (@Valid DadosAutenticacao dados){
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.nome(), dados.senha());
            var authentication = manager.authenticate(authenticationToken);

            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new JWTTokenDates(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}