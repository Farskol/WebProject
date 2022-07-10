package com.finalwebproject.pastrtyshop.entity;

public class OrderDessert extends Entity {
    private int orderDessertId;
    private int dessertCount;
    private Order order;
    private Cake cake;
    private Dessert dessert;

    public OrderDessert(int orderDessertId) {
        this.orderDessertId = orderDessertId;
    }

    public OrderDessert(int dessertCount, Cake cake) {
        this.dessertCount = dessertCount;
        this.cake = cake;
    }

    public OrderDessert(int dessertCount, Dessert dessert) {
        this.dessertCount = dessertCount;
        this.dessert = dessert;
    }

    public OrderDessert(int dessertCount, Order order, Dessert dessert) {
        this.dessertCount = dessertCount;
        this.order = order;
        this.dessert = dessert;
    }

    public OrderDessert(int dessertCount, Order order, Cake cake) {
        this.dessertCount = dessertCount;
        this.order = order;
        this.cake = cake;
    }

    public OrderDessert(int orderDessertId, int dessertCount, Order order, Cake cake) {
        this.orderDessertId = orderDessertId;
        this.dessertCount = dessertCount;
        this.order = order;
        this.cake = cake;
    }

    public OrderDessert(int orderDessertId, int dessertCount, Order order, Dessert dessert) {
        this.orderDessertId = orderDessertId;
        this.dessertCount = dessertCount;
        this.order = order;
        this.dessert = dessert;
    }

    public int getOrderDessertId() {
        return orderDessertId;
    }

    public void setOrderDessertId(int orderDessertId) {
        this.orderDessertId = orderDessertId;
    }

    public int getDessertCount() {
        return dessertCount;
    }

    public void setDessertCount(int dessertCount) {
        this.dessertCount = dessertCount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public Dessert getDessert() {
        return dessert;
    }

    public void setDessert(Dessert dessert) {
        this.dessert = dessert;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (this == null || this.getClass().equals(o.getClass())) return false;

        OrderDessert order = (OrderDessert) o;

        if (this.orderDessertId != 0 ? this.orderDessertId != order.orderDessertId : order.orderDessertId != 0) return false;
        if (this.dessertCount != 0 ? this.dessertCount != order.dessertCount : order.dessertCount != 0) return false;
        if (this.order != null ? !this.order.equals(order.order) : order.order != null) return false;
        if (this.cake != null ? !this.cake.equals(order.cake) : order.cake != null) return false;
        if (this.dessert != null ? !this.dessert.equals(order.dessert) : order.dessert != null) return false;

        return false;
    }

    @Override
    public int hashCode(){
        int result = 18 * (this.orderDessertId != 0 ? this.orderDessertId : 0);
        result = 18 * result + (this.dessertCount != 0 ? this.dessertCount : 0);
        result = 18 * result + (this.order != null ? this.order.hashCode() : 0);
        result = 18 * result + (this.cake != null ? this.cake.hashCode() : 0);
        result = 18 * result + (this.dessert != null ? this.dessert.hashCode() : 0);

        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Order dessert{ ");
        sb.append("Order dessert id: ").append(this.orderDessertId).append(", ");
        sb.append("Dessert count: ").append(this.dessertCount).append(", ");
        sb.append("Oder: ").append(this.order.getOrderId()).append(", ");
        if (this.cake != null) sb.append("Cake: ").append(this.cake.toString()).append("}");
        if (this.dessert != null) sb.append("Dessert: ").append(this.dessert.toString()).append("}");

        return sb.toString();
    }

}
