package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;

import java.util.List;
import java.util.Map;

public interface ClientService {
    List<ArticleDTO> getClients(Map<String, String> params) throws Exception;
}
