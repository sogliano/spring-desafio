package com.example.springdesafio.utils;

import com.example.springdesafio.dto.ArticleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Sorters implements Comparator<ArticleDTO> {
    public List<ArticleDTO> sortByNameAsc (List<ArticleDTO> db){
        List<ArticleDTO> orderedList = db.stream().sorted(Comparator.comparing(ArticleDTO::getName)).collect(Collectors.toList());
        return orderedList;
    }

    public List<ArticleDTO> sortByNameDesc (List<ArticleDTO> db){
        List<ArticleDTO> orderedList = db.stream().sorted(Comparator.comparing(ArticleDTO::getName).reversed()).collect(Collectors.toList());
        return orderedList;
    }

    public List<ArticleDTO> sortByPriceAsc (List<ArticleDTO> db){
        List<ArticleDTO> orderedList = db.stream().sorted(Comparator.comparing(ArticleDTO::getPrice)).collect(Collectors.toList());
        return orderedList;
    }

    public List<ArticleDTO> sortByPriceDesc (List<ArticleDTO> db){
        List<ArticleDTO> orderedList = db.stream().sorted(Comparator.comparing(ArticleDTO::getPrice).reversed()).collect(Collectors.toList());
        return orderedList;
    }

    @Override
    public int compare(ArticleDTO o1, ArticleDTO o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
