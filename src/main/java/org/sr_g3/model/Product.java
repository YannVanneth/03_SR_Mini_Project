package org.sr_g3.model;


import java.time.LocalDate;


public class Product {

    private long product_id;
    private String name;
    private double unit_price;
    private int quantity;
    private LocalDate imported_date;

    public Product() {}

    public Product(long product_id, String name, double unit_price, int quantity, LocalDate imported_date) {
        this.product_id = product_id;
        this.name = name;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.imported_date = imported_date;
    }

    public Product( String name, double unit_price, int quantity, LocalDate imported_date) {
        this.name = name;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.imported_date = imported_date;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getImported_date() {
        return imported_date;
    }

    public void setImported_date(LocalDate imported_date) {
        this.imported_date = imported_date;
    }



}
