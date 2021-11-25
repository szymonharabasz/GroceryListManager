package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.GroceryItem;
import com.szymonharabasz.grocerylistmanager.domain.GroceryList;
import com.szymonharabasz.grocerylistmanager.view.GroceryItemView;
import com.szymonharabasz.grocerylistmanager.view.GroceryListView;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class ListsController {
    private ListsService service;
    private Date creationDate = new Date();
    private List<GroceryListView> lists = new ArrayList<>();
    private String greeting;
    private Logger logger = Logger.getLogger(ListsController.class.getName());

    @Inject
    public ListsController(ListsService service) {
        this.service = service;
        this.greeting = "Yellow";
        fetchLists();
    }

    public List<GroceryListView> getLists() {
        return lists;
    }

    public void setLists(List<GroceryListView> lists) {
        this.lists = lists;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) { this.greeting = greeting; }

    public void editList(String id) {
        findList(id).ifPresent(list -> list.setEdited(true));
    }

    public void saveList(String id) {
        findList(id).ifPresent(list -> {
            list.setEdited(false);
            service.saveList(list.toGroceryList());
        });
    }

    public void expand(String id) {
        findList(id).ifPresent(list -> list.setExpanded(true));
    }

    public void collapse(String id) {
        findList(id).ifPresent(list -> list.setExpanded(false));
    }

    public void removeList(String id) {
        service.removeList(id);
        fetchLists();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void editItem(String id) {
        findItem(id).ifPresent(item -> item.setEdited(true));
    }

    public void saveItem(String id, String listId) {
        findItem(id).ifPresent(item -> {
            item.setEdited(false);
            service.saveItem(item.toGroceryItem(), listId);
        });
    }

    public void removeItem(String id) {
        service.removeItem(id);
        fetchLists();
    }

    public void addList() {
        GroceryListView list = new GroceryListView(UUID.randomUUID().toString(), "", "");
        list.setEdited(true);
        lists.add(list);
    }

    public void addItem(String listId) {
        GroceryItemView item = new GroceryItemView(UUID.randomUUID().toString(), "", "", 0.0f);
        item.setEdited(true);
        findList(listId).map(list -> {
            list.addItem(item);
            service.saveList(list.toGroceryList());
            return list;
        });
    }

    private Optional<GroceryListView> findList(String id) {
        return lists.stream().filter(list -> Objects.equals(list.getId(), id)).findAny();
    }

    private Optional<GroceryItemView> findItem(String id) {
        for (GroceryListView list : lists) {
            for (GroceryItemView item : list.getItems()) {
                if (Objects.equals(item.getId(), id)) return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    private void fetchLists() {
        lists = service.getLists().stream().map(list -> {
            GroceryListView listView = new GroceryListView(list);
            findList(list.getId()).ifPresent(oldListView -> {
                listView.setExpanded(oldListView.isExpanded());
            });
            return listView;
        }).collect(Collectors.toList());
    }
}