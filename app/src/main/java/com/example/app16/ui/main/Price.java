package com.example.app16.ui.main;

import java.time.LocalDate;

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
        return "Price{" +
                "dateOfStock=" + dateOfStock +
                ", stockPrice=" + stockPrice +
                '}';
    }

}
