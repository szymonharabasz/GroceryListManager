package com.szymonharabasz.grocerylistmanager;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.UUID;
import java.util.logging.Logger;

@Singleton
@Startup
public class ListsLoader {
    @Inject
    private ListsService service;
    Logger logger = Logger.getLogger(ListsLoader.class.getName());
    @PostConstruct
    public void loadLists() {
        GroceryList list1 = new GroceryList(generateID(), "Aldi", "Shopping list for Aldi");
        list1.addItem(new GroceryItem("Potatoes", "kg", 1.0f));
        list1.addItem(new GroceryItem("Tomatoes", "kg", 0.5f));
        GroceryList list2 = new GroceryList(generateID(), "Rewe", "Shopping list for Rewe");
        service.addList(list1);
        service.addList(list2);
        logger.severe("Loaded lists: " + service.getLists().toString());
    }
    public String generateID() {
        return UUID.randomUUID().toString();
    }
}
