package com.example.springdesafio.controllers;

import ch.qos.logback.core.net.server.Client;
import com.example.springdesafio.dto.ClientDTO;
import com.example.springdesafio.dto.StatusDTO;
import com.example.springdesafio.dto.TicketDTO;
import com.example.springdesafio.exceptions.ClientExistsException;
import com.example.springdesafio.exceptions.InvalidClientParamsException;
import com.example.springdesafio.exceptions.InvalidParamException;
import com.example.springdesafio.exceptions.ParameterQuantityException;
import com.example.springdesafio.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    // Obtener los clientes (admite hasta dos filtros).
    @GetMapping("/clients")
    public ResponseEntity getClients(@RequestParam Map<String, String> params) throws Exception {
        return new ResponseEntity(clientService.getClients(params), HttpStatus.OK);
    }

    // Enviar un client y almacenarlo en la BD.
    @PostMapping("/clients/add-client")
    public ResponseEntity addClient(@RequestBody ClientDTO client) throws Exception {
        return new ResponseEntity(clientService.addClient(client), HttpStatus.OK);
    }

    // Excepción: cliente ya existente.
    @ExceptionHandler(value={ClientExistsException.class})
    public ResponseEntity<StatusDTO> clientExistsException(ClientExistsException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    // Excepción: parámetro ingresado inválido.
    @ExceptionHandler(value={InvalidParamException.class})
    public ResponseEntity<StatusDTO> invalidParameterException(InvalidParamException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={InvalidClientParamsException.class})
    public ResponseEntity<StatusDTO> invalidClientParamsException(InvalidClientParamsException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={ParameterQuantityException.class})
    public ResponseEntity<StatusDTO> ParameterQuantityException(ParameterQuantityException e){
        StatusDTO statusDTO = new StatusDTO(400, e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.BAD_REQUEST);
    }
}
