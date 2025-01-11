package br.com.nazareth.forum.Forum.Hub.infra.service;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.model.DadosCurso;
import br.com.nazareth.forum.Forum.Hub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public void verifyDuplicationOfCourse(DadosCurso dados) {
        if (cursoRepository.existsByNomeAndCategoria(dados.nome(), dados.categoria())) {
            throw new IllegalArgumentException("Já existe um curso com esse nome e categoria!!");
        }
    }

    public Curso makeNewCourse(DadosCurso dadosCurso) {
        verifyDuplicationOfCourse(dadosCurso);
        Curso curso = new Curso();
        curso.setNome(dadosCurso.nome());
        curso.setCategoria(dadosCurso.categoria());
        return cursoRepository.save(curso);
    }

    public List<DadosCurso> showAllCourses() {
        return cursoRepository.findAll().stream()
                .map(DadosCurso::fromEntity) // Converte cada Curso para DadosCurso
                .toList();
    }
}