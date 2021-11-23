package com.szymonharabasz.grocerylistmanager;

import javax.annotation.PostConstruct;
import javax.el.ValueExpression;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class ListsController {
    private ListsService service;

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

    public void makeEditable(String id) {
        GroceryList list = service.findById(id);
        list.setEdited(true);
    }

    public void saveChages(String id) {
        GroceryList list = service.findById(id);
        list.setEdited(false);
    }

    public void remove(String id) {
        service.removeList(id);
    }

    public void add() {
        service.addList();
    }
}
