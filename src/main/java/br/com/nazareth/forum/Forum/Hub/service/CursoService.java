package br.com.nazareth.forum.Forum.Hub.service;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.model.cursos.*;
import br.com.nazareth.forum.Forum.Hub.repository.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public List<DadosCursosEmDB> showAllCourses() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(DadosCursosEmDB::fromEntity)
                .toList();
    }

    public ResponseEntity excluirCurso(Long id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso com id " + id + " não encontrado."));
        curso.setDeletado(true); // Marca como deletado em vez de excluir fisicamente
        cursoRepository.save(curso);
        return ResponseEntity.ok("Curso excluído com sucesso.");
    }

    public ResponseEntity atualizarCurso(Long id, @Valid UpdateCourse dados) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso com id " + id + " não encontrado."));

        curso.atualizar(dados);
    cursoRepository.save(curso);
    return ResponseEntity.ok(new DadosCursoAtualizado(curso));
    }

    public void setAsDeleted(Long id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado."));
        if (curso.isDeletado()) {
            throw new IllegalStateException("O curso já foi marcado como deletado.");
        }
        curso.setDeletado(true);
        cursoRepository.save(curso);
    }

    public List<ActiveCoursesList> showActiveCourses() {
        List<Curso> cursos = cursoRepository.findAllAtivos();
        return cursos.stream()
                .map(ActiveCoursesList::fromEntity)
                .toList();
    }
}