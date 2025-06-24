package entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "workspace")
public class WorkSpace {

    @Id
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "availability")
    private Boolean availability;

    public WorkSpace() {}

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WorkSpace workSpace)) return false;
        return getId() == workSpace.getId() && getPrice() == workSpace.getPrice()
                && getAvailability() == workSpace.getAvailability()
                && Objects.equals(getType(), workSpace.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getPrice(), getAvailability());
    }
}
