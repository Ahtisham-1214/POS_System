package Model;

import Controller.UnitType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnitTypeDatabase {
    private final Connection connection;
    public UnitTypeDatabase(int id, String name, float conversionToBaseUnit) throws SQLException {
        connection = DatabaseConnection.getConnection();
        insertUnitType(id, name, conversionToBaseUnit);
    }

    public UnitTypeDatabase() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    private void createUnitTypeTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS unit_types (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(20) NOT NULL, " +
                "conversion_to_base_unit FLOAT NOT NULL)";
        try (java.sql.Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    private void insertUnitType(int id, String name, float conversionToBaseUnit) throws SQLException {
        String query = "INSERT INTO unit_types (id, name, conversion_to_base_unit) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query))  {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setFloat(3, conversionToBaseUnit);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<UnitType> getUnitTypes() throws SQLException {
        ArrayList<UnitType> unitTypes = new ArrayList<>();
        String query = "SELECT * FROM unit_types";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    float conversionToBaseUnit = resultSet.getFloat("conversion_to_base_unit");
                    UnitType unitType = new UnitType();
                    unitType.setId(id);
                    unitType.setName(name);
                    unitType.setConversionToBaseUnit(conversionToBaseUnit);
                    unitTypes.add(unitType);
                }
            }
            return unitTypes;
        }
    }

}
