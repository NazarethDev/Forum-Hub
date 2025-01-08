package br.com.nazareth.forum.Forum.Hub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Usuario autor;
    @OneToMany(mappedBy = "autor")
    private List<Resposta> respostas;
    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;
}