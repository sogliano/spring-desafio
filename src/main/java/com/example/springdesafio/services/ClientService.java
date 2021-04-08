package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.ClientDTO;
import com.example.springdesafio.dto.ResponseClientDTO;
import com.example.springdesafio.dto.StatusDTO;

import java.util.List;
import java.util.Map;

public interface ClientService {
    List<ClientDTO> getClients(Map<String, String> params) throws Exception;
    ResponseClientDTO addClient(ClientDTO clientDTO) throws Exception;
}
