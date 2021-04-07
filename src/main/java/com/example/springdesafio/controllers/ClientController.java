package com.example.springdesafio.controllers;

import com.example.springdesafio.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping("/clients")
    public ResponseEntity getArticles(@RequestParam Map<String, String> params) throws Exception {
        return new ResponseEntity(clientService.getClients(params), HttpStatus.OK);
    }


}
