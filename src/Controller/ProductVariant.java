package Controller;

import java.util.ArrayList;

public class ProductVariant {
    private int id;
    private int  productId;
    private float unitQuantity;
    private int unitTypeId;
    private float price;

    public static ArrayList<ProductVariant> productVariants = new ArrayList<ProductVariant>();
    public ProductVariant(int id, int productId, float unitQuantity, int unitTypeId, float price) {
        this.setId(id);
        this.setProductId(productId);
        this.setUnitQuantity(unitQuantity);
        this.setUnitTypeId(unitTypeId);
        this.setPrice(price);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(float unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public int getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(int unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
