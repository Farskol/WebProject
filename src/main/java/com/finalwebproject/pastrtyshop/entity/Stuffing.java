package com.finalwebproject.pastrtyshop.entity;

public class Stuffing extends Entity {

    private int stuffingId;
    private String stuffing;
    private String description;

    public Stuffing(int stuffingId) {
        this.stuffingId = stuffingId;
    }

    public Stuffing(String stuffing, String description) {
        this.stuffing = stuffing;
        this.description = description;
    }

    public Stuffing(int stuffingId, String stuffing, String description) {
        this.stuffingId = stuffingId;
        this.stuffing = stuffing;
        this.description = description;
    }

    public int getStuffingId() {
        return stuffingId;
    }

    public void setStuffingId(int stuffingId) {
        this.stuffingId = stuffingId;
    }

    public String getStuffing() {
        return stuffing;
    }

    public void setStuffing(String stuffing) {
        this.stuffing = stuffing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(this == null || !this.getClass().equals(o.getClass())) return false;

        Stuffing stuffing = (Stuffing) o;

        if(this.stuffingId != 0 ? this.stuffingId != stuffing.stuffingId : stuffing.stuffingId != 0) return false;
        if(this.stuffing != null ? !this.stuffing.equals(stuffing.stuffing) : stuffing.stuffing != null) return false;
        if(this.description != null ? !this.description.equals(stuffing.description) : stuffing.description != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        int result = 15 * (this.stuffingId != 0 ? this.stuffingId : 0);
        result = 15 * result + (this.stuffing != null ? this.stuffing.hashCode() : 0);
        result = 15 * result + (this.description != null ? this.description.hashCode() : 0);

        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Stuffing {");
        sb.append("Stuffing id: ").append(this.stuffingId).append(", ");
        sb.append("Stuffing: ").append(this.stuffing).append(", ");
        sb.append("Description: ").append(this.description).append("}");
        return sb.toString();
    }
}
