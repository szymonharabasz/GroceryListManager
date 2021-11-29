package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.GroceryItem;
import com.szymonharabasz.grocerylistmanager.domain.GroceryList;
import com.szymonharabasz.grocerylistmanager.domain.Salt;
import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.HashingService;
import com.szymonharabasz.grocerylistmanager.service.ListsService;
import com.szymonharabasz.grocerylistmanager.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
import java.util.logging.Logger;

import static com.szymonharabasz.grocerylistmanager.Utils.generateID;

@Singleton
//@Startup
public class Initializer {
    @Inject
    private ListsService listsService;
    @Inject
    private UserService userService;
    @Inject
    private HashingService hashingService;
    private Logger logger = Logger.getLogger(Initializer.class.getName());
    @PostConstruct
    public void loadLists() {
        String listId1 = generateID();
        GroceryList list1 = new GroceryList(listId1, "Aldi", "Shopping list for Aldi");
        list1.addItem(new GroceryItem(generateID(), "Potatoes", "kg", 1.0f));
        list1.addItem(new GroceryItem(generateID(), "Tomatoes", "kg", 0.5f));
        GroceryList list2 = new GroceryList(generateID(), "Rewe", "Shopping list for Rewe");
        listsService.saveList(list1);
        listsService.saveList(list2);
        Salt salt = new Salt(generateID(), HashingService.createSalt());
        hashingService.save(salt);
        User user1 = new User(salt.getUserId(), "Carl",
                HashingService.createHash("pwd", salt), "carl@example.com");
        user1.addListId(listId1);
        userService.save(user1);
        logger.severe("Loaded lists: " + listsService.getLists().toString());
    }
}
