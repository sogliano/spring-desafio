package com.example.springdesafio.repositories;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.CartDTO;
import com.example.springdesafio.dto.TicketDTO;
import com.example.springdesafio.exceptions.AvailabilityException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface ArticleRepository {
    List<ArticleDTO> getArticles(Map<String,String> parametros);
    List<ArticleDTO> getArticlesByProductId(Integer productId);
    List<ArticleDTO> getArticlesByName(String name);
    List<ArticleDTO> getArticlesByCategory(String category);
    List<ArticleDTO> getArticlesByBrand(String brand);
    List<ArticleDTO> getArticlesByPrice(Double price);
    List<ArticleDTO> getArticlesByShipping(Boolean freeShipping);
    List<ArticleDTO> getArticlesByPrestige(Integer prestige);
    List<ArticleDTO> sortArticles(Integer orderType);
    TicketDTO makePurchase(List<ArticleDTO> articles) throws AvailabilityException;
    CartDTO getCart();
}