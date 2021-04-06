package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.ResponseDTO;
import com.example.springdesafio.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleDTO> getArticles(Map<String, String> params) {
        String order = null;
        Map<String,String> parametros = new HashMap<>();
        if(params.containsKey("order")){
            order = params.get("order");
            params.remove("order");
        }
        if(params.size() > 2){
            System.out.println("Hay mas de dos parametros."); // tirar excepcion
        } else {
            for(Map.Entry<String,String> entry: params.entrySet()){
                if(validateParam(entry.getKey())){
                    parametros.put(entry.getKey(),entry.getValue());
                } // else { Tirar excepcion (averiguar si se pide que se tire excepcion o que se limpien los parametros) }
            }
            // Agrego el parámetro order quitado anteriormente.
            parametros.put("order", order);
        }
        return articleRepository.getArticles(parametros);
    }

    private boolean validateParam(String p){
        boolean isValid = false;
        String[] valid = new String[]{"name","category","brand", "price", "freeShipping", "prestige"};
        for(String v: valid){
            if(v.equals(p)){
                isValid = true;
            }
        }
        return isValid;
    }

    private void mostrarParams(List<String> params){
        for(String p:params){
            System.out.println(p);
        }
    }
}
