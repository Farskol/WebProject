package com.finalwebproject.pastrtyshop.entity;

public class ClientDiscount extends Entity {
    private int discountId;
    private double value;
    private String nameOfDiscount;

    public ClientDiscount(int discountId, double value, String nameOfDiscount){
        this.value = value;
        this.nameOfDiscount = nameOfDiscount;
        this.discountId = discountId;
    }

    public ClientDiscount(double value, String nameOfDiscount) {
        this.value = value;
        this.nameOfDiscount = nameOfDiscount;
    }

    public ClientDiscount(int discountId) {
        this.discountId = discountId;
    }

    public ClientDiscount(){}

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNameOfDiscount() {
        return nameOfDiscount;
    }

    public void setNameOfDiscount(String nameOfDiscount) {
        this.nameOfDiscount = nameOfDiscount;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        ClientDiscount discount = (ClientDiscount) o;

        if(this.discountId != 0 ? this.discountId != discount.discountId : discount.discountId != 0) return false;
        if(this.value != 0 ? this.value != discount.value : discount.value != 0) return false;
        if(this.nameOfDiscount != null ? !this.nameOfDiscount.equals(discount.nameOfDiscount) : discount.nameOfDiscount != null) return false;
        return true;
    }

    @Override
    public int hashCode(){
        int result = this.value !=0 ? (int) this.value : 0;
        result = 11 * result + (this.discountId != 0 ? this.discountId : 0);
        result = 11 * result + (this.nameOfDiscount != null ? this.nameOfDiscount.hashCode() : 0);

        return result;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder("Discount{");
        sb.append("Discount id: ").append(this.discountId).append(", ");
        sb.append("Value: ").append(this.value).append(", ");
        sb.append("Name of discount: ").append(this.nameOfDiscount).append("}");
        return sb.toString();
    }
}
