package com.example.springdesafio.controllers;

import com.example.springdesafio.dto.StatusDTO;
import com.example.springdesafio.dto.TicketDTO;
import com.example.springdesafio.exceptions.*;
import com.example.springdesafio.services.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;

    // Obtener todos los artículos.
    @GetMapping("/articles")
    public ResponseEntity getArticles(@RequestParam Map<String, String> params) throws Exception {
        return new ResponseEntity(articleService.getArticles(params), HttpStatus.OK);
    }

    // Visualizar el carrito.
    @GetMapping("/articles/cart")
    public ResponseEntity getArticles() throws Exception {
        return new ResponseEntity(articleService.getCart(), HttpStatus.OK);
    }

    // Enviar orden de compra.
    @PostMapping("/articles/purchase-request")
    public ResponseEntity makePurchase(@RequestBody TicketDTO ticket) throws AvailabilityException, IOException, InvalidArticleException {
        return new ResponseEntity(articleService.makePurchase(ticket.getArticles()), HttpStatus.OK);
    }

    // Excepción: más de dos parametros solicitados.
    @ExceptionHandler(value={ParameterQuantityException.class})
    public ResponseEntity<StatusDTO> parameterQuantityExceeded(ParameterQuantityException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    // Excepción: parámetro ingresado inválido.
    @ExceptionHandler(value={InvalidParamException.class})
    public ResponseEntity<StatusDTO> invalidParameterException(InvalidParamException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    // Excepción: item solicitado sin stock.
    @ExceptionHandler(value={AvailabilityException.class})
    public ResponseEntity<StatusDTO> availabilityException(AvailabilityException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    // Excepción: carrito vacío.
    @ExceptionHandler(value={EmptyCartException.class})
    public ResponseEntity<StatusDTO> emptyCartException(EmptyCartException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    // Excepción: numero ingresado inválido.
    @ExceptionHandler(value={InvalidNumberException.class})
    public ResponseEntity<StatusDTO> invalidNumberException(InvalidNumberException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    // Excepción: articulo inválido (solo valida por productId para que no moleste tanto, es muy sencillo agregar los demás parametros).
    @ExceptionHandler(value={InvalidArticleException.class})
    public ResponseEntity<StatusDTO> invalidArticleException(InvalidArticleException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }
}
