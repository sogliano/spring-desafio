package com.example.springdesafio.repositories;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.ClientDTO;
import com.example.springdesafio.dto.StatusDTO;
import com.example.springdesafio.exceptions.ClientExistsException;
import com.example.springdesafio.exceptions.ParameterQuantityException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ClientRepository {
    List<ClientDTO> getClients(Map<String,String> parametros) throws ParameterQuantityException, IOException;
    List<ClientDTO> getClientsByName(String name);
    List<ClientDTO> getClientsByEmail(String email);
    List<ClientDTO> getClientsByCellphone(int cellphone);
    List<ClientDTO> getClientsByProvince(String province);
    void writeDB(List<ClientDTO> clients) throws IOException;
    ClientDTO addClient(ClientDTO clientDTO) throws ClientExistsException, IOException;
}
