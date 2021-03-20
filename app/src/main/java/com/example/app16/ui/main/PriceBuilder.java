package com.example.app16.ui.main;

import java.time.LocalDate;

public class PriceBuilder {


    private LocalDate dateOfStock;
    private Double stockPrice;

    public PriceBuilder() {
    }

    public PriceBuilder setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
        return this;
    }

    public PriceBuilder setDateOfStock(LocalDate dateOfStock) {
        this.dateOfStock = dateOfStock;
        return this;
    }

    public Price build() {
        Price price = new Price();
        price.setDateOfStock(dateOfStock);
        price.setStockPrice(stockPrice);
        return price;

    }
}
