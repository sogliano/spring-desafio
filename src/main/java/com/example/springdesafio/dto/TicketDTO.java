package com.example.springdesafio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TicketDTO {
    private Integer id = 0;
    private List<ArticleDTO> articles;
    private Integer total;

    public TicketDTO(List<ArticleDTO> articles, Integer total){
        this.id++;
        this.articles = articles;
        this.total = total;
    }
}