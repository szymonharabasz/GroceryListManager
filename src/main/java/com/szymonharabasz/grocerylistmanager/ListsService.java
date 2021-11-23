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

    public GroceryList findById(String id) {
        for (GroceryList list : lists) if (Objects.equals(list.getId(), id)) return list;
        return null;
    }

    public void removeList(String id) {
        lists = lists.stream().filter(l -> !Objects.equals(l.getId(), id)).collect(Collectors.toList());
    }

    public void addList() {
        GroceryList list = new GroceryList(UUID.randomUUID().toString(), "", "");
        list.setEdited(true);
        lists.add(list);
    }
}
