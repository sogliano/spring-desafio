package com.example.springdesafio.repositories;

import com.example.springdesafio.dto.ArticleDTO;

import java.util.List;
import java.util.Map;

public interface ClientRepository {
    List<ArticleDTO> getClients(Map<String,String> parametros);
    List<ArticleDTO> getClientsByName(String name);
    List<ArticleDTO> getClientsByEmail(String email);
    List<ArticleDTO> getClientsByCellphone(int cellphone);
}
