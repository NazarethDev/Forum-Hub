package br.com.nazareth.forum.Forum.Hub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @PostMapping
    public ResponseEntity answerQuestion(){
        return null;
    }
}
