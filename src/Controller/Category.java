package Controller;

import Model.CategoryDatabase;

import java.util.ArrayList;

public class Category {
    public static ArrayList<Category> categories;

    static {
        try {
            categories = new CategoryDatabase().getCategories();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int id;
    private String name;

    public Category(int id, String name) throws Exception {
        this.setId(id);
        this.setName(name);
        new CategoryDatabase(this.getId(), this.getName());
    }

    public Category() {

    }

    public Category(int id) throws Exception {
        new CategoryDatabase(id);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Category ID must be greater than 0");
        }
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

    public void insertCategory() throws Exception {
        new CategoryDatabase(this.getId(), this.getName());
    }

    public void updateCategory() throws Exception{
        new CategoryDatabase().updateCategory(this.getId(), this.getName());
    }

    public void deleteCategory() throws Exception{
        new CategoryDatabase(this.getId());
    }
}
