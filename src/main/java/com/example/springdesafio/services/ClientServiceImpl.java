package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.exceptions.InvalidNumberException;
import com.example.springdesafio.exceptions.InvalidParamException;
import com.example.springdesafio.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    @Override
    public List<ArticleDTO> getClients(Map<String, String> params) throws Exception {
        Map<String,String> parametros = new HashMap<>();

        for(Map.Entry<String,String> entry: params.entrySet()){
            if(validateParam(entry.getKey())){
                if(validateValue(entry.getValue(), entry.getKey())){
                    parametros.put(entry.getKey(),entry.getValue());
                } else {
                    throw new InvalidParamException(entry.getKey());
                }
            }
            else
                { throw new InvalidParamException(entry.getKey()); }
        }
        return clientRepository.getClients(parametros);
    }

    // Validacion de Keys de parametros.
    private boolean validateParam(String p){
        String[] valid = new String[]{"name","email","cellphone"};
        boolean isValid = false;
        for(String v: valid){
            if(v.equals(p)){ isValid = true; }
        }
        return isValid;
    }

    // Validacion de Name y Email.
    private boolean validateValue(String v, String type){
        boolean isValid = true;
        switch(type){
            case "name": if(v.length() < 2){ isValid = false; };
            break;
            case "email": if(!v.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
                isValid = false;
            }
            break;
        }
        return isValid;
    }
}
