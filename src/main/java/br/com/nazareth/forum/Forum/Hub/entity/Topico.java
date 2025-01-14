package br.com.nazareth.forum.Forum.Hub.entity;

import br.com.nazareth.forum.Forum.Hub.model.DadosAtualizacao;
import br.com.nazareth.forum.Forum.Hub.model.DadosNewTopic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Topicos")
@AllArgsConstructor
@NoArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualicacao;

    @OneToMany (mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    private boolean answered;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;


    public Topico (){}

    public Topico(DadosNewTopic novoTopico, Usuario autor, Curso curso){
        this.titulo = novoTopico.titulo();
        this.mensagem = novoTopico.mensagem();
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualicacao = null;
        this.curso = curso;
        this.answered = false;
    }

    public void atualizar(DadosAtualizacao dados, Usuario autor, Curso curso) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = curso;
        this.dataAtualicacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public LocalDateTime getDataAtualicacao() {
        return dataAtualicacao;
    }

    public void setDataAtualicacao(LocalDateTime dataAtualicacao) {
        this.dataAtualicacao = dataAtualicacao;
    }

}
