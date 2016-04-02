package org.joseloarca.bean;
public class Stock {
    private int idStock;
    private String stock;
    public Stock() {
    }
    public Stock(int idStock, String stock) {
        this.idStock = idStock;
        this.stock = stock;
    }
    public int getIdStock() {
        return idStock;
    }
    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }
    public String getStock() {
        return stock;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }
    public String toString(){
        return getStock();
    }
}
