package br.com.nazareth.forum.Forum.Hub.service;

import br.com.nazareth.forum.Forum.Hub.entity.Curso;
import br.com.nazareth.forum.Forum.Hub.model.DadosCurso;
import br.com.nazareth.forum.Forum.Hub.model.DadosCursosEmDB;
import br.com.nazareth.forum.Forum.Hub.repository.CursoRepository;
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
        List<Curso> cursos = cursoRepository.findAll(); // Obtém as entidades do banco de dados
        return cursos.stream()
                .map(DadosCursosEmDB::fromEntity) // Converte para o DTO
                .toList();
    }

    public ResponseEntity excluirCurso(Long id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso com id " + id + " não encontrado."));
        curso.setDeletado(true); // Marca como deletado em vez de excluir fisicamente
        cursoRepository.save(curso);
        return ResponseEntity.ok("Curso excluído com sucesso.");
    }
}