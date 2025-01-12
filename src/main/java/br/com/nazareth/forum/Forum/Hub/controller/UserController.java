package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.model.DadosAutenticacao;
import br.com.nazareth.forum.Forum.Hub.model.DadosNovoUsuario;
import br.com.nazareth.forum.Forum.Hub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados){
        return usuarioService.entrar(dados);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar (@RequestBody @Valid DadosNovoUsuario dados){
        return usuarioService.createUser(dados);
    }

}