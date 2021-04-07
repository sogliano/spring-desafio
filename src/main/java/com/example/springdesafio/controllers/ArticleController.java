package com.example.springdesafio.controllers;

import com.example.springdesafio.dto.ErrorDTO;
import com.example.springdesafio.exceptions.InvalidParamException;
import com.example.springdesafio.exceptions.ParameterQuantityException;
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
    public ResponseEntity getArticles(@RequestParam Map<String, String> params) throws Exception {
        return new ResponseEntity(articleService.getArticles(params), HttpStatus.OK);
    }

    @ExceptionHandler(value={ParameterQuantityException.class})
    public ResponseEntity<ErrorDTO> parameterQuantityExceeded(ParameterQuantityException e){
        ErrorDTO errorDTO = new ErrorDTO("Quantity of parameters exceeded.", e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidParamException.class})
    public ResponseEntity<ErrorDTO> invalidParameterException(InvalidParamException e){
        ErrorDTO errorDTO = new ErrorDTO("One .", e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
