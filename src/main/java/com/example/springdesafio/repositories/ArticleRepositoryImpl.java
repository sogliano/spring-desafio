package com.example.springdesafio.repositories;

import com.example.springdesafio.dto.ArticleDTO;
import com.example.springdesafio.dto.CartDTO;
import com.example.springdesafio.dto.TicketDTO;
import com.example.springdesafio.exceptions.AvailabilityException;
import com.example.springdesafio.exceptions.EmptyCartException;
import com.example.springdesafio.exceptions.InvalidArticleException;
import com.example.springdesafio.utils.Sorters;
import org.springframework.stereotype.Repository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private List<ArticleDTO> articlesDatabaseOriginal = loadDataBase();
    private List<ArticleDTO> articlesDatabaseAux = loadDataBase();
    private CartDTO cart = new CartDTO();
    private Sorters sorter = new Sorters();

    public ArticleRepositoryImpl() throws IOException {
    }

    // Obtener artículos según parámetros de filtro.
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

    @Override
    public List<ArticleDTO> getArticlesByParameter(String parameter, String value) {
        return null;
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

    // Envío de solicitud de compra.
    // Cada vez que alguien realiza una solicitud de compra, se agrega al carrito y se actualiza el csv de la BD.
    @Override
    public TicketDTO makePurchase(List<ArticleDTO> articles) throws AvailabilityException, IOException, InvalidArticleException {
        float total = 0;
        for(ArticleDTO articleRequest : articles){
            if(!articleExists(articleRequest.getProductId())){
                throw new InvalidArticleException(articleRequest.getProductId());
            } else {
                for(ArticleDTO articleDB : this.articlesDatabaseOriginal){
                    if(articleRequest.getProductId() == articleDB.getProductId()){
                        if(articleDB.getQuantity() < articleRequest.getQuantity()){
                            throw new AvailabilityException(articleRequest.getName());
                        }
                        articleRequest.setPrice(null);
                        articleDB.setQuantity(articleDB.getQuantity() - articleRequest.getQuantity());
                        total += articleDB.getPrice() * articleRequest.getQuantity();
                    }
                }
            }
        }
        writeDatabase(this.articlesDatabaseOriginal);
        TicketDTO ticket = new TicketDTO(articles, (int)total);
        cart.addTicket(ticket);
        return ticket;
    }

    // Retorno del carrito.
    @Override
    public CartDTO getCart() throws EmptyCartException {
        if(cart.getTickets().size() < 1) throw new EmptyCartException();
        return this.cart;
    }

    // Método de update del .csv.
    public void writeDatabase(List<ArticleDTO> articles) throws IOException {
        String csvFile = "src/main/resources/dbProductos.csv";
        FileWriter writer = new FileWriter(csvFile);
        String collect = "productId,name,category,brand,price,quantity,freeShipping,prestige\n";
        for(ArticleDTO article: articles) {
            String prestigeStr = "";
            for(int i = 0; i < article.getPrestige(); i++){
                prestigeStr += "*";
            }
            collect += article.getProductId() + "," + article.getName() + "," + article.getCategory() + "," + article.getBrand() + "," + "$" + Math.round(article.getPrice()) + "," +
                    article.getQuantity() + "," + article.getFreeShipping() + "," + prestigeStr + "\n";

        }
        writer.write(collect);
        writer.close();
    }

    // Método de carga del HashMap de "BD".
    private List<ArticleDTO> loadDataBase() throws IOException {
        List<ArticleDTO> articles = new ArrayList<>();
        BufferedReader br;
        String line;
        String separator = ",";
        String[] data;
        String csvFile = "src/main/resources/dbProductos.csv";

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
        writeDatabase(articles);
        return articles;
    }

    private boolean articleExists(int productId){
        boolean exists = false;
        for(ArticleDTO a : articlesDatabaseOriginal){
            if(a.getProductId() == productId){
                exists = true;
            }
        }
        return exists;
    }
}
