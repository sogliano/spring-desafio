package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.ResponseTicketDTO;
import com.example.springdesafio.exceptions.AvailabilityException;
import com.example.springdesafio.exceptions.InvalidArticleException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(Map<String, String> params) throws Exception;
    ResponseTicketDTO makePurchase(List<ArticleDTO> articles) throws AvailabilityException, IOException, InvalidArticleException;
}
