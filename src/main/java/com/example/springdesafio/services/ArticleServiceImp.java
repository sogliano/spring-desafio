package com.example.springdesafio.services;

import com.example.springdesafio.dto.ResponseDTO;
import com.example.springdesafio.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImp implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImp(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }
}
