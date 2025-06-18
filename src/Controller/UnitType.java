package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import Model.DatabaseConnection;
import Model.UnitTypeDatabase;

public class UnitType {
    private int id;
    private String name;
    private float conversionToBaseUnit;

    public static ArrayList<UnitType> unitTypes;

    static {
        try {
            unitTypes = new UnitTypeDatabase().getUnitTypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UnitType(int id, String name, float conversionToBaseUnit) throws SQLException {
        this.setId(id);
        this.setName(name);
        this.setConversionToBaseUnit(conversionToBaseUnit);
//        unitTypes.add(this);
        new UnitTypeDatabase(this.getId(), this.getName(), this.getConversionToBaseUnit());
    }

    public UnitType(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Unit Type ID must be greater than 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getConversionToBaseUnit() {
        return conversionToBaseUnit;
    }

    public void setConversionToBaseUnit(float conversionToBaseUnit) {
        this.conversionToBaseUnit = conversionToBaseUnit;
    }

    public void updateUnitType() throws SQLException {
        new UnitTypeDatabase().updateUnitType(this.getId(), this.getName(), this.getConversionToBaseUnit());
    }

    public void deleteUnitType() throws SQLException {
        new UnitTypeDatabase().deleteUnitType(this.getId());
    }
}
