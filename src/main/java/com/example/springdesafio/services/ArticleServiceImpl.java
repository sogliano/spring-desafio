package com.example.springdesafio.services;

import com.example.springdesafio.dto.*;
import com.example.springdesafio.exceptions.*;
import com.example.springdesafio.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    // Validamos los parámetros antes de consultar a la BD.
    @Override
    public List<ArticleDTO> getArticles(Map<String, String> params) throws Exception {
        String order = null;
        Map<String,String> parametros = new HashMap<>();
        // Removing order if exists (temporary).
        if(params.containsKey("order")){
            order = params.get("order");
            params.remove("order");
        }
        // Check if parameters quantity is OK.
        if(params.size() > 2){
            throw new ParameterQuantityException();
        } else {
            for(Map.Entry<String,String> entry: params.entrySet()){
                if(validateParam(entry.getKey())){
                    parametros.put(entry.getKey(),entry.getValue());
                } else {
                    throw new InvalidParamException(entry.getKey());
                    }
                if(entry.getKey().equals("productId") || entry.getKey().equals("order") || entry.getKey().equals("price")){
                    if(!entry.getValue().matches("[0-9]+")){ throw new InvalidNumberException(entry.getKey(), entry.getValue()); }
                }
            }
            // Putting back order to the map.
            if(order != null){ parametros.put("order", order); }
            if(parametros.containsKey("freeShipping")){
                if(parametros.get("freeShipping").equals("SI")){
                    parametros.put("freeShipping", "true");
                } else {
                    parametros.put("freeShipping", "false");
                }
            }
        }
        return articleRepository.getArticles(parametros);
    }

    @Override
    public ResponseTicketDTO makePurchase(List<ArticleDTO> articles) throws AvailabilityException, IOException, InvalidArticleException {
        return new ResponseTicketDTO(articleRepository.makePurchase(articles), new StatusDTO(200, "La solicitud de compra se completó con éxito."));
    }

    // Validador de Keys de los params.
    private boolean validateParam(String p){
        String[] valid = new String[]{"productId","name","category","brand", "price", "freeShipping", "prestige"};
        boolean isValid = false;
        for(String v: valid){
            if(v.equals(p)){ isValid = true; }
        }
        return isValid;
    }

    public CartDTO getCart() throws EmptyCartException {
        return articleRepository.getCart();
    }
}
