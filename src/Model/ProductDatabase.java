package Model;

import Controller.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDatabase {
    private Connection connection;

    public ProductDatabase(int id, String name, int categoryId, int UnitTypeId) throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public ProductDatabase() throws SQLException{
        connection = DatabaseConnection.getConnection();
    }

    private void createProductTable() throws SQLException{
        String query = "CREATE TABLE IF NOT EXISTS products (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(30) NOT NULL, " +
                "category_id INT NOT NULL, " +
                "unit_type_id INT NOT NULL, " +
                "price_per_unit FLOAT)";
        try(java.sql.Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
        }
    }

    private void insertProduct(int id, String name, int categoryId, int UnitTypeId) throws SQLException{
        String query = "insert into products  (id, name, category_id, unit_type_id) values (?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, categoryId);
            preparedStatement.setInt(4, UnitTypeId);
            preparedStatement.executeUpdate();

        }
    }

    public ArrayList<Product> getProducts() throws SQLException{
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try(java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int categoryId = resultSet.getInt("category_id");
                    int UnitTypeId = resultSet.getInt("unit_type_id");
                    float pricePerUnit = resultSet.getFloat("price_per_unit");
                    Product product = new Product();
                    product.setId(id);
                    product.setName(name);
                    product.setCategoryID(categoryId);
                    product.setUnitTypeId(UnitTypeId);
                    product.setPricePerUnit(pricePerUnit);
                    products.add(product);
                }
            }
            return products;
        }
    }

}
