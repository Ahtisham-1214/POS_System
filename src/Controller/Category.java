package Controller;

import java.util.ArrayList;

public class Category {
    public static ArrayList<Category> categories = new ArrayList<Category>();
    private int id;
    private String name;

    public Category(int id, String name) {
        this.setId(id);
        this.setName(name);
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
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product Name cannot be null or empty");
        }
        this.name = name;
    }
}
