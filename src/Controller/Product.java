package Controller;

import Model.ProductDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private int categoryID;
    private int unitTypeId;
    private float pricePerUnit;

    public static ArrayList<Product> products;

    static {
        try {
            products = new ProductDatabase().getProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product(int id, String name, int categoryID, int unitTypeId, float pricePerUnit) {
        this.setId(id);
        this.setName(name);
        this.setCategoryID(categoryID);
        this.setUnitTypeId(unitTypeId);
        this.setPricePerUnit(pricePerUnit);
    }

    public Product(int id, String name, int categoryID, int unitTypeId ) {
        this.setId(id);
        this.setName(name);
        this.setCategoryID(categoryID);
        this.setUnitTypeId(unitTypeId);
    }

    public Product(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(int unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public static int getTotalProducts() {
        return products.size();
    }
}
