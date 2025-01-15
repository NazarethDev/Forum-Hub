package br.com.nazareth.forum.Forum.Hub.entity;

import br.com.nazareth.forum.Forum.Hub.model.cursos.Categoria;
import jakarta.persistence.*;


@Table(name = "curso")
@Entity
public class Curso {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Categoria categoria;
    private String nome;
    private boolean deletado = false;

    public Curso (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDeletado() {
        return deletado;
    }

    public void setDeletado(boolean deletado) {
        this.deletado = deletado;
    }
}