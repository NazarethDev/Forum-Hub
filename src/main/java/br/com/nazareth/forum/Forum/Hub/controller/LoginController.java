package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.entity.Usuario;
import br.com.nazareth.forum.Forum.Hub.model.DadosAutenticacao;
import br.com.nazareth.forum.Forum.Hub.security.JWTTokenDates;
import br.com.nazareth.forum.Forum.Hub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.nome(),dados.senha());
        var autentication = manager.authenticate(authenticationToken);
        var token = tokenService.gerarToken((Usuario) autentication.getPrincipal());

        return ResponseEntity.ok(new JWTTokenDates(token));
    }
}
