package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.GroceryItem;
import com.szymonharabasz.grocerylistmanager.domain.GroceryList;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.UUID;
import java.util.logging.Logger;

@Singleton
//@Startup
public class Initializer {
    @Inject
    private ListsService service;
    private Logger logger = Logger.getLogger(Initializer.class.getName());
    @PostConstruct
    public void loadLists() {
        GroceryList list1 = new GroceryList(generateID(), "Aldi", "Shopping list for Aldi");
        list1.addItem(new GroceryItem(generateID(), "Potatoes", "kg", 1.0f));
        list1.addItem(new GroceryItem(generateID(), "Tomatoes", "kg", 0.5f));
        GroceryList list2 = new GroceryList(generateID(), "Rewe", "Shopping list for Rewe");
        service.saveList(list1);
        service.saveList(list2);
        logger.severe("Loaded lists: " + service.getLists().toString());
    }
    public String generateID() {
        return UUID.randomUUID().toString();
    }
}
