package com.example.sapimarket.ui.home;

public class Model {

    private String imageUrl;
    private String name;
    private String category;
    private String price;
    private String description;
    private String seller;

    public Model(String imageUrl, String name, String category, String price, String description, String seller) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.seller = seller;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
