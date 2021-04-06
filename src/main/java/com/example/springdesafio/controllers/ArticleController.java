package com.example.springdesafio.controllers;

import com.example.springdesafio.services.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;

    @GetMapping("/articles")
    public ResponseEntity getArticles(@RequestParam Map<String, String> params){
        return new ResponseEntity(articleService.getArticles(params), HttpStatus.OK);
    }
}
