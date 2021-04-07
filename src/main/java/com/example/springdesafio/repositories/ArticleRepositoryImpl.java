package com.example.springdesafio.repositories;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.CartDTO;
import com.example.springdesafio.dto.TicketDTO;
import com.example.springdesafio.exceptions.AvailabilityException;
import com.example.springdesafio.utils.Sorters;
import org.springframework.stereotype.Repository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private List<ArticleDTO> articlesDatabaseOriginal = loadDataBase();
    private List<ArticleDTO> articlesDatabaseAux = loadDataBase();
    private CartDTO cart = new CartDTO();
    private Sorters sorter = new Sorters();

    private List<ArticleDTO> loadDataBase(){
        List<ArticleDTO> articles = new ArrayList<>();
        String csvFile = "src/main/resources/dbProductos.csv";
        BufferedReader br;
        String line;
        String separator = ",";
        String[] data;

        try{
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null){
                data = line.split(separator);
                if(data[6].equals("SI")){ data[6]="true"; } else { data[6]="false"; }
                if(!data[0].equals("productId")){
                    ArticleDTO article = new ArticleDTO(Integer.valueOf(data[0]), data[1], data[2], data[3],
                            Double.valueOf(data[4].replace("$", "").replace(".", "")),
                            Integer.valueOf(data[5]), Boolean.valueOf(data[6]), Integer.valueOf(data[7].length()));
                    articles.add(article);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticles(Map<String,String> params) {
        this.articlesDatabaseAux = articlesDatabaseOriginal;
        if(params.size() == 0){ return this.articlesDatabaseOriginal; }
        if(params.size() == 1 || params.size() == 2 || params.size() == 3){
            for(Map.Entry<String,String> entry: params.entrySet()){
                // Gets by params.
                if(entry.getKey().equals("productId")){ articlesDatabaseAux = getArticlesByProductId(Integer.valueOf(entry.getValue())); };
                if(entry.getKey().equals("name")){ articlesDatabaseAux = getArticlesByName(entry.getValue()); };
                if(entry.getKey().equals("category")){ articlesDatabaseAux = getArticlesByCategory(entry.getValue()); };
                if(entry.getKey().equals("brand")){ articlesDatabaseAux = getArticlesByBrand(entry.getValue()); };
                if(entry.getKey().equals("price")){ articlesDatabaseAux = getArticlesByPrice(Double.valueOf(entry.getValue())); };
                if(entry.getKey().equals("freeShipping")) { articlesDatabaseAux = getArticlesByShipping(Boolean.valueOf(entry.getValue()));}
                if(entry.getKey().equals("prestige")){ articlesDatabaseAux = getArticlesByPrestige(Integer.valueOf(entry.getValue())); };

                // Sort by order type.
                if(entry.getKey().equals("order")){ articlesDatabaseAux = sortArticles(Integer.valueOf(entry.getValue()));}
            }
        }

        return articlesDatabaseAux;
    }

    // Filter by param methods:
    @Override
    public List<ArticleDTO> getArticlesByProductId(Integer productId){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.articlesDatabaseAux){ if(a.getProductId() == productId){ aux.add(a); } }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByName(String name){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.articlesDatabaseAux){ if(a.getName().equalsIgnoreCase(name)){ aux.add(a); } }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByCategory(String category){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.articlesDatabaseAux){ if(a.getCategory().equalsIgnoreCase(category)){ aux.add(a); } }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByBrand(String brand){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.articlesDatabaseAux){ if(a.getBrand().equalsIgnoreCase(brand)){ aux.add(a); } }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByPrice(Double price){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.articlesDatabaseAux){ if(a.getPrice() == price){ aux.add(a); } }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByShipping(Boolean freeShipping){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.articlesDatabaseAux){ if(a.getFreeShipping() == freeShipping){ aux.add(a); } }
        return aux;
    }

    @Override
    public List<ArticleDTO> getArticlesByPrestige(Integer prestige){
        List<ArticleDTO> aux = new ArrayList<>();
        for(ArticleDTO a : this.articlesDatabaseAux){ if(a.getPrestige() == prestige){ aux.add(a); } }
        return aux;
    }

    // Sort methods:
    @Override
    public List<ArticleDTO> sortArticles(Integer orderType){
        switch (orderType){
            case 0: this.articlesDatabaseAux = sorter.sortByNameAsc(this.articlesDatabaseAux);
                break;
            case 1: this.articlesDatabaseAux = sorter.sortByNameDesc(this.articlesDatabaseAux);
                break;
            case 2: this.articlesDatabaseAux = sorter.sortByPriceDesc(this.articlesDatabaseAux);
                break;
            case 3: this.articlesDatabaseAux = sorter.sortByPriceAsc(this.articlesDatabaseAux);
                break;
        }
        return articlesDatabaseAux;
    }

    @Override
    public TicketDTO makePurchase(List<ArticleDTO> articles) throws AvailabilityException {
        float total = 0;
        for(ArticleDTO articleRequest : articles){
            for(ArticleDTO articleDB : this.articlesDatabaseOriginal){
                if(articleRequest.getProductId() == articleDB.getProductId()){
                    if(articleDB.getQuantity() < articleRequest.getQuantity()){ throw new AvailabilityException(articleRequest.getName()); }
                    articleDB.setQuantity(articleDB.getQuantity() - articleRequest.getQuantity());
                    total += articleDB.getPrice() * articleRequest.getQuantity();
                }
            }
        }
        TicketDTO ticket = new TicketDTO(articles, (int)total);
        System.out.println("Ticket creado: " + ticket.toString());
        cart.addTicket(ticket);
        return ticket;
    }

    @Override
    public CartDTO getCart() {
        return this.cart;
    }
}
