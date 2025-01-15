package br.com.nazareth.forum.Forum.Hub.service;

import br.com.nazareth.forum.Forum.Hub.entity.Topico;
import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.topics.ShowTopicDetails;
import br.com.nazareth.forum.Forum.Hub.model.user.DadosAutenticacao;
import br.com.nazareth.forum.Forum.Hub.model.user.DadosNovoUsuario;
import br.com.nazareth.forum.Forum.Hub.model.user.DadosRespostaNovoUsuario;
import br.com.nazareth.forum.Forum.Hub.repository.AnswerRepository;
import br.com.nazareth.forum.Forum.Hub.repository.TopicRepository;
import br.com.nazareth.forum.Forum.Hub.repository.UsuarioRepository;
import br.com.nazareth.forum.Forum.Hub.security.JWTTokenDates;
import br.com.nazareth.forum.Forum.Hub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private AnswerRepository answerRepository;

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

    public Page<ShowTopicDetails> getTopicosByUsuario(Long usuarioId, Pageable pageable) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            Page<Topico> topicosPage = topicRepository.findByAutor(usuario.get(), pageable);

            Page<ShowTopicDetails> topicsDetails = topicosPage.map(topico -> new ShowTopicDetails(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensagem(),
                    topico.getDataCriacao(),
                    topico.isAnswered(),
                    topico.getCurso().getNome(),
                    topico.getAutor().getNome(),
                    topico.getAutor().getEmail()
            ));

            return topicsDetails;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }

//    public Page<RespostasListagem> getRespostasByUsuario(Long usuarioId, Pageable pageable) {
//        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
//        if (usuario.isPresent()) {
//            Page<Resposta> respostasPage = answerRepository.findRespostasByAutor(usuario.get(), pageable);
//
//            Page<RespostasPorUsuario> respostasPorUsuario = respostasPage.map(resposta -> new RespostasPorUsuario(
//                    resposta.getId(),
//                    resposta.getMensagem(),
//                    resposta.getDataCriacao(),
//                    resposta.getDataAtualizacao(),
//                    resposta.getTopico().getId()
//            ));
//            return respostasPorUsuario;
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
//        }
//    }


}