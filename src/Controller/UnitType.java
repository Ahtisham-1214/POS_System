package Controller;

import java.util.ArrayList;

public class UnitType {
    private int id;
    private String name;
    private float conversionToBaseUnit;

    public static ArrayList<UnitType> unitTypes = new ArrayList<UnitType>();

    public UnitType(int id, String name, float conversionToBaseUnit) {
        this.setId(id);
        this.setName(name);
        this.setConversionToBaseUnit(conversionToBaseUnit);
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

    public float getConversionToBaseUnit() {
        return conversionToBaseUnit;
    }

    public void setConversionToBaseUnit(float conversionToBaseUnit) {
        this.conversionToBaseUnit = conversionToBaseUnit;
    }
}
