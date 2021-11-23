package com.szymonharabasz.grocerylistmanager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class ListsService {
    private List<GroceryList> lists = new ArrayList<>();

    public List<GroceryList> getLists() {
        return lists;
    }

    public void setLists(List<GroceryList> lists) {
        this.lists = lists;
    }

    public void addList(GroceryList list) {
        lists.add(list);
    }

    public GroceryList findList(String id) {
        for (GroceryList list : lists) if (Objects.equals(list.getId(), id)) return list;
        return null;
    }

    public GroceryItem findItem(String id) {
        for (GroceryList list : lists) {
            for (GroceryItem item : list.getItems()) {
                if (Objects.equals(item.getId(), id)) return item;
            }
        }
        return null;
    }

    public void removeList(String id) {
        lists = lists.stream().filter(l -> !Objects.equals(l.getId(), id)).collect(Collectors.toList());
    }

    public void removeItem(String id) {
        for (GroceryList list : lists) {
            list.setItems(
                    list.getItems().stream().filter(item ->
                            !Objects.equals(item.getId(), id)).collect(Collectors.toList())
            );
        }
    }

    public void addList() {
        GroceryList list = new GroceryList(UUID.randomUUID().toString(), "", "");
        list.setEdited(true);
        lists.add(list);
    }

    public void addItem(String listId) {
        GroceryItem item = new GroceryItem(UUID.randomUUID().toString(), "", "", 0.0f);
        item.setEdited(true);
        findList(listId).addItem(item);
        System.err.println("A new item added");
    }
}
