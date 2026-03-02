package org.sr_g3.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Setter
@Getter
public class Product {

    private long product_id;
    private String name;
    private double unit_price;
    private int quantity;
    private LocalDate imported_date;


    public Product(long product_id, String name, double unit_price, int quantity, LocalDate imported_date) {
        this.product_id = product_id;
        this.name = name;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.imported_date = imported_date;
    }

}
