package com.szymonharabasz.grocerylistmanager.view;

import com.szymonharabasz.grocerylistmanager.domain.GroceryItem;

import java.io.Serializable;
import java.util.Objects;

public class GroceryItemView implements Serializable {
    private String id;
    private boolean done;
    private String name;
    private String unit;
    private float quantity;
    private boolean edited;

    public GroceryItemView(String id, boolean done, String name, String unit, float quantity) {
        this.id = id;
        this.done = done;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.edited = false;
    }

    public GroceryItemView(GroceryItem item) {
        this(item.getId(), item.isDone(), item.getName(), item.getUnit(), item.getQuantity());
    }

    public GroceryItem toGroceryItem() {
        return new GroceryItem(id, done, name, unit, quantity);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroceryItemView that = (GroceryItemView) o;
        return Float.compare(that.quantity, quantity) == 0 && name.equals(that.name) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, unit, quantity);
    }

    @Override
    public String toString() {
        return "GroceryItem{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
