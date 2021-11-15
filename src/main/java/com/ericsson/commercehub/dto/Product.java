package com.ericsson.commercehub.dto;

import lombok.Data;

@Data
public class Product {

    private String name;

    private String imageURL;

    private double price;

    private String siteName;

    private String productURL;

    private String currencyCode;

    public void setName(String name) {
        this.name = name;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", price=" + price +
                ", siteName='" + siteName + '\'' +
                ", productURL='" + productURL + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
