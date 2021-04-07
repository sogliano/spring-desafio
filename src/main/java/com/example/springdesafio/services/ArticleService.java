package com.example.springdesafio.services;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.CartDTO;
import com.example.springdesafio.dto.ResponseDTO;
import com.example.springdesafio.dto.TicketDTO;
import com.example.springdesafio.exceptions.AvailabilityException;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(Map<String, String> params) throws Exception;
    ResponseDTO makePurchase(List<ArticleDTO> articles) throws AvailabilityException;
}
