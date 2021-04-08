package com.example.springdesafio.repositories;

import ch.qos.logback.core.net.server.Client;
import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.ClientDTO;
import com.example.springdesafio.dto.StatusDTO;
import com.example.springdesafio.exceptions.ClientExistsException;
import com.example.springdesafio.exceptions.ParameterQuantityException;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private List<ClientDTO> clientDatabase = loadDataBase();
    private List<ClientDTO> clientDatabaseAux = loadDataBase();

    public ClientRepositoryImpl() throws IOException {
    }

    @Override
    public List<ClientDTO> getClients(Map<String, String> params) throws ParameterQuantityException, IOException {
        this.clientDatabaseAux = loadDataBase();
        if(params.size() == 1 || params.size() == 2){
            for(Map.Entry<String,String> entry: params.entrySet()){
                // Gets by params.
                if(entry.getKey().equals("name")){ clientDatabaseAux = getClientsByName(entry.getValue()); };
                if(entry.getKey().equals("email")){ clientDatabaseAux = getClientsByEmail(entry.getValue()); };
                if(entry.getKey().equals("cellphone")){ clientDatabaseAux = getClientsByCellphone(Integer.valueOf(entry.getValue())); };
                if(entry.getKey().equals("province")){ clientDatabaseAux = getClientsByProvince(entry.getValue()); };
            }
            return clientDatabaseAux;
        }
        if(params.size()>2){
            throw new ParameterQuantityException();
        }
        return clientDatabaseAux;
    }

    @Override
    public List<ClientDTO> getClientsByName(String name) {
        List<ClientDTO> aux = new ArrayList<>();
        for(ClientDTO c : clientDatabase){
            if(c.getName().equals(name)){
                aux.add(c);
            }
        }
        return aux;
    }

    @Override
    public List<ClientDTO> getClientsByEmail(String email) {
        List<ClientDTO> aux = new ArrayList<>();
        for(ClientDTO c : clientDatabase){
            if(c.getEmail().equals(email)){
                aux.add(c);
            }
        }
        return aux;
    }

    @Override
    public List<ClientDTO> getClientsByCellphone(int cellphone) {
        List<ClientDTO> aux = new ArrayList<>();
        for(ClientDTO c : clientDatabase){
            if(c.getCellphone() == cellphone){
                aux.add(c);
            }
        }
        return aux;
    }

    @Override
    public List<ClientDTO> getClientsByProvince(String province) {
        List<ClientDTO> aux = new ArrayList<>();
        for(ClientDTO c : clientDatabase){
            if(c.getProvince().equals(province)){
                aux.add(c);
            }
        }
        return aux;
    }

    private HashMap getterMethods(ClientDTO c){
        HashMap aux = new HashMap();
        aux.put("name", c.getName());
        aux.put("email", c.getEmail());
        aux.put("cellphone", c.getCellphone());
        aux.put("province", c.getProvince());
        return null;
    }

    @Override
    public ClientDTO addClient(ClientDTO clientRequest) throws ClientExistsException, IOException {
        if(clientExists(clientRequest.getEmail())) throw new ClientExistsException();
        this.clientDatabaseAux.add(clientRequest);
        writeDB(this.clientDatabaseAux);
        return clientRequest;
    }

    @Override
    // Método de update del .csv.
    public void writeDB(List<ClientDTO> clients) throws IOException {
        String csvFile = "src/main/resources/dbClientes.csv";
        FileWriter writer = new FileWriter(csvFile);
        String collect = "name,email,cellphone,province\n";
        for(ClientDTO client: clients) {
            collect += client.getName() + "," + client.getEmail() + "," + client.getCellphone() + "," + client.getProvince() + "\n";
        }
        writer.write(collect);
        writer.close();
    }

    private boolean clientExists(String email){
        for(ClientDTO c : clientDatabaseAux){
            if(c.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    // Método de carga de la lista de "BD".
    private List<ClientDTO> loadDataBase() throws IOException {
        List<ClientDTO> clients = new ArrayList<>();
        BufferedReader br;
        String line;
        String separator = ",";
        String[] data;
        String csvClients = "src/main/resources/dbClientes.csv";
        try{
            br = new BufferedReader(new FileReader(csvClients));
            while((line = br.readLine()) != null){
                data = line.split(separator);
                if(!data[0].equals("name")){
                    ClientDTO client = new ClientDTO(data[0], data[1], Integer.valueOf(data[2]), data[3]);
                    clients.add(client);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        for(ClientDTO c: clients){
            System.out.println(c.getName());
        }
        writeDB(clients);
        return clients;
    }
}
