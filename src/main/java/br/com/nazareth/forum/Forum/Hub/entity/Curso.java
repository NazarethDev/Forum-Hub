package br.com.nazareth.forum.Forum.Hub.entity;

import br.com.nazareth.forum.Forum.Hub.model.Categoria;
import jakarta.persistence.*;

@Table(name = "curso")
@Entity
public class Curso {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Categoria curso;
    private String nome;
}