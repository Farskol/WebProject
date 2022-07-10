package com.finalwebproject.pastrtyshop.entity;

public class Dessert extends Entity {

    private int dessertId;
    private String description;
    private double cost;
    private String name;
    private String type;

    public Dessert(int dessertId, String description, double cost, String name, String type) {
        this.dessertId = dessertId;
        this.description = description;
        this.cost = cost;
        this.name = name;
        this.type = type;
    }

    public Dessert(String description, double cost, String name, String type) {
        this.description = description;
        this.cost = cost;
        this.name = name;
        this.type = type;
    }

    public Dessert(int dessertId) {
        this.dessertId = dessertId;
    }

    public int getDessertId() {
        return dessertId;
    }

    public void setDessertId(int dessertId) {
        this.dessertId = dessertId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dessert dessert = (Dessert) o;

        if (this.dessertId != 0 ? this.dessertId != dessert.dessertId : dessert.dessertId != 0);
        if (this.description != null ? !this.description.equals(dessert.description) : dessert.description != null) return false;
        if (this.cost != 0 ? this.cost != dessert.cost : dessert.cost != 0) return false;
        if (this.name != null ? !this.name.equals(dessert.name) : dessert.name != null) return false;
        if (this.type != null ? !this.type.equals(dessert.name) : dessert.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17 * (this.dessertId != 0 ? this.dessertId : 0);
        result = 17 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 17 * result + (this.cost != 0 ? (int)this.cost : 0);
        result = 17 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 17 * result + (this.type != null ? this.name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Dessert {");
        sb.append("Dessert  id: ").append(this.dessertId).append(", ");
        sb.append("Cost: ").append(this.cost).append(", ");
        sb.append("Name: ").append(this.name).append(", ");
        sb.append("Type: ").append(this.type).append("}");
        return sb.toString();
    }
}
