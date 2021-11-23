package com.szymonharabasz.grocerylistmanager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class ListsController {
    private ListsService service;
    private Date creationDate = new Date();

    private String greeting;
    @Inject
    public ListsController(ListsService service) {
        this.service = service;
        this.greeting = "Yellow";
    }

    private Logger logger = Logger.getLogger(ListsController.class.getName());

    public List<GroceryList> getLists() {
        return service.getLists();
    }

    public void setLists(List<GroceryList> lists) {
        service.setLists(lists);
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) { this.greeting = greeting; }

    public void editList(String id) {
        GroceryList list = service.findList(id);
        list.setEdited(true);
    }

    public void saveList(String id) {
        GroceryList list = service.findList(id);
        list.setEdited(false);
    }

    public void expand(String id) {
        GroceryList list = service.findList(id);
        list.setExpanded(true);
    }

    public void collapse(String id) {
        GroceryList list = service.findList(id);
        list.setExpanded(false);
    }

    public void removeList(String id) {
        service.removeList(id);
    }

    public void addList() {
        service.addList();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void editItem(String id) {
        GroceryItem item = service.findItem(id);
        item.setEdited(true);
    }

    public void saveItem(String id) {
        GroceryItem item = service.findItem(id);
        item.setEdited(false);
    }

    public void removeItem(String id) {
        System.err.println("Request to remove an item");
        service.removeItem(id);
    }

    public void addItem(String listId) {
        System.err.println("Request to add an item");
        service.addItem(listId);
    }
}
