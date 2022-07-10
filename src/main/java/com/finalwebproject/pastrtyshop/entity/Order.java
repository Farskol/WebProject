package com.finalwebproject.pastrtyshop.entity;

import java.util.Date;

public class Order extends Entity {

    private int orderId;
    private Date orderDate;
    private double totalPrice;
    private Client client;
    private OrderStatus orderStatus;
    private Date inWhatDate;
    private String firstName;
    private String secondName;
    private String phoneNumber;

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public Order(double totalPrice, OrderStatus orderStatus, Date inWhatDate, String firstName, String secondName, String phoneNumber) {
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.inWhatDate = inWhatDate;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Order(double totalPrice, Client client, OrderStatus orderStatus, Date inWhatDate) {
        this.totalPrice = totalPrice;
        this.client = client;
        this.orderStatus = orderStatus;
        this.inWhatDate = inWhatDate;
    }

    public Order(double totalPrice, Client client, OrderStatus orderStatus, Date inWhatDate, String firstName, String secondName, String phoneNumber) {
        this.totalPrice = totalPrice;
        this.client = client;
        this.orderStatus = orderStatus;
        this.inWhatDate = inWhatDate;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Order(int orderId, Date orderDate, double totalPrice, Client client, OrderStatus orderStatus, Date inWhatDate, String firstName, String secondName, String phoneNumber) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.client = client;
        this.orderStatus = orderStatus;
        this.inWhatDate = inWhatDate;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Order(int orderId, Date orderDate, double totalPrice, OrderStatus orderStatus, Date inWhatDate, String firstName, String secondName, String phoneNumber) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.inWhatDate = inWhatDate;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Order(int orderId, Date orderDate, double totalPrice, Client client, OrderStatus orderStatus, Date inWhatDate, String firstName, String phoneNumber) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.client = client;
        this.orderStatus = orderStatus;
        this.inWhatDate = inWhatDate;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public Order(int orderId, Date orderDate, double totalPrice, OrderStatus orderStatus, Date inWhatDate, String firstName, String phoneNumber) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.inWhatDate = inWhatDate;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getInWhatDate() {
        return inWhatDate;
    }

    public void setInWhatDate(Date inWhatDate) {
        this.inWhatDate = inWhatDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || !o.getClass().equals(this.getClass())) return false;

        Order order = (Order) o;

        if (this.orderId != 0 ? this.orderId != order.orderId : order.orderId != 0) return false;
        if (this.orderDate != null ? !this.orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (this.totalPrice != 0 ? this.totalPrice != order.totalPrice : order.totalPrice != 0) return false;
        if (this.client != null ? !this.client.equals(order.client) : order.client != null) return false;
        if (this.orderStatus != null ? !this.orderStatus.equals(order.orderStatus) : order.orderStatus != null) return false;
        if (this.inWhatDate != null ? !this.inWhatDate.equals(order.inWhatDate) : order.inWhatDate != null) return false;
        if (this.firstName != null ? !this.firstName.equals(order.firstName) : order.firstName != null) return false;
        if (this.secondName != null ? !this.secondName.equals(order.secondName) : order.secondName != null) return false;
        if (this.phoneNumber != null ? !this.phoneNumber.equals(order.phoneNumber) : order.phoneNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode (){
        int result = 14 * (this.orderId != 0 ? this.orderId : 0);
        result = 14 * result + (this.orderDate != null ? this.orderDate.hashCode() : 0);
        result = 14 * result + (this.totalPrice != 0 ? (int) this.totalPrice : 0);
        result = 14 * result + (this.client != null ? this.client.hashCode() : 0);
        result = 14 * result + (this.orderStatus != null ? this.orderStatus.hashCode() : 0);
        result = 14 * result + (this.inWhatDate != null ? this.inWhatDate.hashCode() : 0);
        result = 14 * result + (this.firstName != null ? this.firstName.hashCode() : 0);
        result = 14 * result + (this.secondName != null ? this.secondName.hashCode() : 0);
        result = 14 * result + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0);

        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Order {");
        sb.append("Order id: ").append(this.orderId).append(", ");
        if (this.orderDate != null) sb.append("Order data: ").append(this.orderDate.toString()).append(", ");
        sb.append("Total price: ").append(this.totalPrice).append(", ");
        if (this.client != null ) sb.append("Client: ").append(this.client.toString()).append(", ");
        if (this.orderStatus != null) sb.append("Order status: ").append(this.orderStatus.toString()).append(", ");
        if (this.inWhatDate != null) sb.append("In what date: ").append(this.inWhatDate.toString()).append(", ");
        sb.append("First name: ").append(this.firstName).append(", ");
        sb.append("Second name: ").append(this.secondName).append(", ");
        sb.append("Phone number: ").append(this.phoneNumber).append("}");

        return sb.toString();
    }
}
