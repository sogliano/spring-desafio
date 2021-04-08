package com.example.springdesafio.services;

import com.example.springdesafio.dto.ClientDTO;
import com.example.springdesafio.dto.ResponseClientDTO;
import com.example.springdesafio.dto.StatusDTO;
import com.example.springdesafio.exceptions.InvalidClientParamsException;
import com.example.springdesafio.exceptions.InvalidParamException;
import com.example.springdesafio.exceptions.ParameterQuantityException;
import com.example.springdesafio.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Validación de parametros.
    @Override
    public List<ClientDTO> getClients(Map<String, String> params) throws Exception {
        Map<String,String> parametros = new HashMap<>();
        if(params.size()>2) {
            throw new ParameterQuantityException();
        } else {
            for(Map.Entry<String,String> entry: params.entrySet()){
                if(!validateParam(entry.getKey())){
                    throw new InvalidParamException(entry.getKey());
                }
                if(!validateValue(entry.getValue(), entry.getKey())){
                    throw new InvalidParamException(entry.getKey());
                } else {
                    parametros.put(entry.getKey(),entry.getValue());
                }
            }
        }

        return clientRepository.getClients(parametros);
    }

    @Override
    public ResponseClientDTO addClient(ClientDTO c) throws Exception {
        if(c.getName() == null || c.getEmail() == null || c.getCellphone() == null || c.getProvince() == null){
            throw new InvalidClientParamsException();
        }
        if(!validateValue(c.getName(), "name") || !validateValue(c.getEmail(), "email") || !validateValue(c.getProvince(), "province")){
            throw new InvalidParamException();
        }
        return new ResponseClientDTO(clientRepository.addClient(c), new StatusDTO(200, "El cliente fue agregado a la base de datos con éxito."));
    }

    // Validación de Keys de parámetros.
    private boolean validateParam(String p){
        String[] valid = new String[]{"name","email","cellphone","province"};
        boolean isValid = false;
        for(String v: valid){
            if(v.equals(p)){ isValid = true; }
        }
        return isValid;
    }

    // Validación de strings.
    private boolean validateValue(String v, String type){
        boolean isValid = true;
        switch(type){
            case "name": if(v.split(" ").length < 2 ){
                isValid = false;
            };
            break;
            case "province": if(v.length() < 5){
                isValid = false;
            };
            break;
            case "email": if(!v.matches("^\\S+@\\S+$")){
                isValid = false;
            }
            break;
        }
        return isValid;
    }
}
