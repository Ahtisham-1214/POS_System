package Model;

import Controller.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class CategoryDatabase {
    private final Connection connection;

    public CategoryDatabase(int id, String name) throws Exception {
        connection = DatabaseConnection.getConnection();
        insertCategory(id, name);
    }

    public CategoryDatabase() throws Exception {
        connection = DatabaseConnection.getConnection();
    }

    public CategoryDatabase(int id) throws Exception {
        connection = DatabaseConnection.getConnection();
        deleteCategory(id);
    }
    private void createCategoryTable() throws Exception {
        String query = "CREATE TABLE IF NOT EXISTS categories (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(30) NOT NULL)";
    }

    private void insertCategory(int id, String name) throws Exception {
        String query = "insert into categories  (id, name) values (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        }
    }

    public void updateCategory(int id, String name) throws Exception {
        String query = "UPDATE categories SET name = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    private void deleteCategory(int id) throws Exception {
        String query = "DELETE FROM categories WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Category> getCategories() throws Exception {
        ArrayList<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try(java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Category category = new Category();
                    category.setId(id);
                    category.setName(name);
                    categories.add(category);
                }
            }
        }
        return categories;
    }


}
