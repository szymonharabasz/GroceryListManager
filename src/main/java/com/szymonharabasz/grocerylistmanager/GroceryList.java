package com.szymonharabasz.grocerylistmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class GroceryList implements Serializable {
    private String id;
    private String name;
    private String description;
    private List<GroceryItem> items = new ArrayList<>();
    private boolean edited;
    private boolean expanded;

    private Logger logger = Logger.getLogger(GroceryList.class.getName());

    public GroceryList(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.edited = false;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroceryList that = (GroceryList) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        logger.severe("Nmme of the list changes to " + name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroceryItem> getItems() { return items; }

    void setItems(List<GroceryItem> items) {
        this.items = items;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isEdited() {
        return edited;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "GroceryList{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void addItem(GroceryItem item) {
        items.add(item);
    }

    public List<GroceryItem> findAll() {
        return items;
    }
}
