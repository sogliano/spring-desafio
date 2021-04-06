package com.example.springdesafio.repositories;

import com.example.springdesafio.dto.ArticleDTO;
import org.springframework.stereotype.Repository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private List<ArticleDTO> database = loadDataBase();

    private List<ArticleDTO> loadDataBase(){
        List<ArticleDTO> articles = new ArrayList<>();
        String csvFile = "src/main/resources/dbProductos.csv";
        BufferedReader br = null;
        String line = "";
        String separator = ",";
        String[] data = null;

        try{
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null){
                data = line.split(separator);
                if(!data[0].equals("name")){
                    ArticleDTO article = new ArticleDTO(Integer.valueOf(data[0]), data[1], data[2], data[3],
                            Double.valueOf(data[4].replace("$", "").replace(".", "")),
                            Integer.valueOf(data[5]), Boolean.valueOf(data[6]), Integer.valueOf(data[7].length()));
                    articles.add(article);
                    System.out.println(article.toString());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticles(Map<String,String> params) {
        loadDataBase(); // santi
        if(params.size() == 0){
            return this.database;
        }
        if(params.size() == 1){
            // Filtro por un parametro.
        } else if(params.size() == 2){
            // Filtro por dos parametros.
        } else if(params.size() == 3){
            // Filtro y ordeno.
        }
        // Filtro y ordeno según el valor del params[2]
        return this.database;
    }

    @Override
    public List<ArticleDTO> getArticlesByName(String name){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.database){
            if(a.getName().equalsIgnoreCase(name)){
                aux.add(a);
            }
        }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByCategory(String category){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.database){
            if(a.getCategory().equalsIgnoreCase(category)){
                aux.add(a);
            }
        }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByBrand(String brand){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.database){
            if(a.getBrand().equalsIgnoreCase(brand)){
                aux.add(a);
            }
        }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByPrice(Double price){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.database){
            if(a.getPrice() == price){
                aux.add(a);
            }
        }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByShipping(Boolean freeShipping){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.database){
            if(a.getFreeShipping() == freeShipping){
                aux.add(a);
            }
        }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByPrestige(Integer prestige){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.database){
            if(a.getPrestige() == prestige){
                aux.add(a);
            }
        }
        return aux;
    }
}
