package br.com.nazareth.forum.Forum.Hub.controller;

import br.com.nazareth.forum.Forum.Hub.infra.service.CursoService;
import br.com.nazareth.forum.Forum.Hub.model.DadosCurso;
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


    @PostMapping
    public ResponseEntity registerNewCourse(@RequestBody @Valid DadosCurso dadosCurso, UriComponentsBuilder uriBuilder){
        try{
            var newCourse = cursoService.makeNewCourse(dadosCurso);
            var uri = uriBuilder.path("/curso/{id}").buildAndExpand(newCourse.getId()).toUri();
            return ResponseEntity.created(uri).body("Curso cadastrado com sucesso!");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DadosCurso>> showRegisterCourses() {
        List<DadosCurso> cursos = cursoService.showAllCourses();
        return ResponseEntity.ok(cursos); // Retorna a lista de DadosCurso
    }



}