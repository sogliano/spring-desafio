package com.example.springdesafio.repositories;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.ClientDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientRepositoryImpl implements ClientRepository {

    private List<ClientDTO> clientDatabase = new ArrayList<>();

    @Override
    public List<ArticleDTO> getClients(Map<String, String> parametros) {
        return null;
    }

    @Override
    public List<ArticleDTO> getClientsByName(String name) {
        return null;
    }

    @Override
    public List<ArticleDTO> getClientsByEmail(String email) {
        return null;
    }

    @Override
    public List<ArticleDTO> getClientsByCellphone(int cellphone) {
        return null;
    }
}
