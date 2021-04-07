package com.example.springdesafio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TicketDTO {
    private static int count = 0;
    private int id;
    private List<ArticleDTO> articles;
    private Integer total;

    public TicketDTO(List<ArticleDTO> articles, Integer total){
        this.articles = articles;
        this.total = total;
        this.id = this.count;
        this.count++;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "id=" + id +
                ", articles=" + articles +
                ", total=" + total +
                '}';
    }
}