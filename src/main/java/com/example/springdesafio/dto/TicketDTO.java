package com.example.springdesafio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TicketDTO {
    private Integer id = 0;
    private List<ArticleDTO> articles;
    private Double total;

    public TicketDTO(List<ArticleDTO> articles, Double total){
        this.id++;
        this.articles = articles;
        this.total = total;
    }
}


