package main.java.entity;

import java.io.Serializable;

public class WorkSpace implements Serializable {

    private int id;
    private String type;
    private int price;
    private boolean availability;

    public WorkSpace(int id, String type, int price, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.availability = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return String.format("Space with id %d of type \"%s\" with price %d", id, type, price);
    }
}
