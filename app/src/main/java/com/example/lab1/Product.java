package com.example.lab1;

public class Product {
    private String name;
    private String label;
    private String type;
    private String nutrition;
    private String color;
    private boolean isStar;

    public Product(String name, String label, String type, String nutrition, String color) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.nutrition = nutrition;
        this.color = color;
        this.isStar = false;
    }

    public String getName() { return name; }

    public String getLabel() { return label; }

    public String getType() { return type; }

    public String getNutrition() { return nutrition; }

    public String getColor() { return color; }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public void setColor(String color) { this.color = color; }

    public boolean getIsStar() { return isStar; }

    public  void setIsStar(boolean isStar) { this.isStar = isStar; }
}
