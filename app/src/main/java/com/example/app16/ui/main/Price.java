package com.example.app16.ui.main;

import java.time.LocalDate;
import java.util.Objects;

public class Price {

    private LocalDate dateOfStock;
    private Double stockPrice;

    public LocalDate getDateOfStock() {
        return dateOfStock;
    }

    public void setDateOfStock(LocalDate dateOfStock) {
        this.dateOfStock = dateOfStock;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "{" +
                "\"dateOfStock\":" + "\""+dateOfStock  +"\""+
                ", \"stockPrice\":" + "\""+ stockPrice + "\""+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return dateOfStock.equals(price.dateOfStock) &&
                stockPrice.equals(price.stockPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfStock, stockPrice);
    }
}
