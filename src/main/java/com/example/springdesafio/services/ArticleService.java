package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(Map<String, String> params);
}
