package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.ResponseDTO;
import com.example.springdesafio.exceptions.InvalidParamException;
import com.example.springdesafio.exceptions.ParameterQuantityException;
import com.example.springdesafio.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }
    @Override
    public List<ArticleDTO> getArticles(Map<String, String> params) throws ParameterQuantityException, InvalidParamException {
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
                if(validateParam(entry.getKey())){ parametros.put(entry.getKey(),entry.getValue()); } else { throw new InvalidParamException(); }
            }
            // Putting back order to the map.
            if(order != null){ parametros.put("order", order); }
        }
        return articleRepository.getArticles(parametros);
    }

    // 
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
}
