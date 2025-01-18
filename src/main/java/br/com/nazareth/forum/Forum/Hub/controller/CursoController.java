package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.model.cursos.ActiveCoursesList;
import br.com.nazareth.forum.Forum.Hub.model.cursos.DadosCursosEmDB;
import br.com.nazareth.forum.Forum.Hub.model.cursos.UpdateCourse;
import br.com.nazareth.forum.Forum.Hub.service.CursoService;
import br.com.nazareth.forum.Forum.Hub.model.cursos.DadosCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping("/cadastrar")
    public ResponseEntity registerNewCourse(@RequestBody @Valid DadosCurso dadosCurso, UriComponentsBuilder uriBuilder){
        try{
            var newCourse = cursoService.makeNewCourse(dadosCurso);
            var uri = uriBuilder.path("/curso/{id}").buildAndExpand(newCourse.getId()).toUri();
            return ResponseEntity.created(uri).body("Curso cadastrado com sucesso!");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DadosCursosEmDB>> showRegisterCourses() {
        List<DadosCursosEmDB> cursos = cursoService.showAllCourses();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping
    public ResponseEntity<List<ActiveCoursesList>> showActiveCourses() {
        List<ActiveCoursesList> cursos = cursoService.showActiveCourses();
        return ResponseEntity.ok(cursos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable Long id){
        return cursoService.excluirCurso(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourse dados){
        return cursoService.atualizarCurso(id,dados);
    }

    @DeleteMapping("/logical/{id}")
    public ResponseEntity<Void> logicalDeleteCourse(@PathVariable Long id) {
        cursoService.setAsDeleted(id);
        return ResponseEntity.noContent().build();
    }
}