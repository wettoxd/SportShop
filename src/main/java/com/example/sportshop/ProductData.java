package com.example.sportshop;

import java.sql.SQLException;


public class ProductData {
    DataBase db = new DataBase();
    private final String name;
    private final int cost;
    private final String desc;
    private final int have;
    private String photo;
    private final int id;
    private final int supplier_id;
    private int category_id;


    public ProductData(int id, String name, int category_id, int price, int stockQuantity,int supplier_id, String description, String photo) {
        this.id=id;
        this.name = name;
        this.cost = price;
        this.category_id=category_id;
        this.have= stockQuantity;
        this.desc = description;
        this.photo = photo;
        this.supplier_id=supplier_id;
    }

    public String getName() {
        return this.name;
    }
    public int getCategory() {
        return this.category_id;
    }
    public int getCost() {
        return this.cost;
    }
    public String getDesc() {
        return this.desc;
    }
    public int getHave() {
        return this.have;
    }
    public String getPhoto() {return this.photo;}
    public int getSupplier_id() {return this.supplier_id;}
    public int getID() {
        return this.id;
    }
    public String getCategoryName() throws SQLException, ClassNotFoundException {
        return (db.getCategory(getCategory()));
    }
}


