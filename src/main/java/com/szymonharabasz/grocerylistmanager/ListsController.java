package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.User;
import com.szymonharabasz.grocerylistmanager.service.ListsService;
import com.szymonharabasz.grocerylistmanager.service.UserService;
import com.szymonharabasz.grocerylistmanager.view.GroceryItemView;
import com.szymonharabasz.grocerylistmanager.view.GroceryListView;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ListsController implements Serializable {
    private final ListsService service;
    private final UserService userService;
    @Inject
    private SecurityContext securityContext;
    private Date creationDate = new Date();
    private List<GroceryListView> lists = new ArrayList<>();
    private String greeting;
    private Logger logger = Logger.getLogger(ListsController.class.getName());

    @Inject
    public ListsController(ListsService listsService, UserService userService) {
        this.service = listsService;
        this.userService = userService;
        this.greeting = "Yellow";
    }

    public List<GroceryListView> getLists() {
        fetchLists();
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
        String principalName = securityContext.getCallerPrincipal().getName();
        userService.findUser(principalName).ifPresent(user -> {
            lists = service.getLists().stream()
                    .filter(list -> user.hasListId(list.getId()))
                    .map(list -> {
                        GroceryListView listView = new GroceryListView(list);
                        findList(list.getId()).ifPresent(oldListView -> {
                            listView.setExpanded(oldListView.isExpanded());
                            listView.setEdited(oldListView.isEdited());
                        });
                        return listView;
                    }).collect(Collectors.toList());
        });
    }

    private String goToLogin() {
        return "login";
    }
}