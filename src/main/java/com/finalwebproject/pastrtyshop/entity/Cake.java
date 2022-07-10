package com.finalwebproject.pastrtyshop.entity;

public class Cake extends Entity {

    private int cakeId;
    private double cost;
    private String designDescription;
    private double weight;
    private Stuffing stuffing;

    public Cake(int cakeId) {
        this.cakeId = cakeId;
    }

    public Cake(int cakeId, double cost, String designDescription, double weight, Stuffing stuffing) {
        this.cakeId = cakeId;
        this.cost = cost;
        this.designDescription = designDescription;
        this.weight = weight;
        this.stuffing = stuffing;
    }

    public Cake(double cost, String designDescription, double weight, Stuffing stuffing) {
        this.cost = cost;
        this.designDescription = designDescription;
        this.weight = weight;
        this.stuffing = stuffing;
    }

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDesignDescription() {
        return designDescription;
    }

    public void setDesignDescription(String designDescription) {
        this.designDescription = designDescription;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Stuffing getStuffing() {
        return stuffing;
    }

    public void setStuffing(Stuffing stuffing) {
        this.stuffing = stuffing;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (this == null || this.getClass().equals(o.getClass())) return false;

        Cake cake = (Cake) o;

        if (this.cakeId != 0 ? this.cakeId != cake.cakeId : cake.cakeId != 0) return false;
        if (this.cost != 0 ? this.cost != cake.cost : cake.cost != 0) return false;
        if (this.designDescription != null ? !this.designDescription.equals(cake.designDescription) : cake.designDescription != null) return false;
        if (this.weight != 0 ? this.weight != cake.weight : cake.weight != 0) return false;
        if (this.stuffing != null ? !this.stuffing.equals(cake.stuffing) : cake.stuffing != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        int result = 16 * (this.cakeId != 0 ? this.cakeId : 0);
        result = 16 * result + (this.cost != 0 ? (int)this.cost : 0);
        result = 16 * result + (this.designDescription != null ? this.designDescription.hashCode() : 0);
        result = 16 * result + (this.weight != 0 ? (int)this.weight : 0);
        result = 16 * result + (this.stuffing != null ? this.stuffing.hashCode() : 0);

        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Cake {");
        sb.append("Cake id: ").append(this.cakeId).append(", ");
        sb.append("Cost: ").append(this.cost).append(", ");
        sb.append("Design description: ").append(this.designDescription).append(", ");
        sb.append("Weight: ").append(this.weight).append(", ");
        if (this.stuffing != null) sb.append("Stuffing: ").append(this.stuffing.toString()).append("}");

        return sb.toString();
    }
}
