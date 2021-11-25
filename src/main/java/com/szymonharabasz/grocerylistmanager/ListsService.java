package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.GroceryItem;
import com.szymonharabasz.grocerylistmanager.domain.GroceryList;
import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import jakarta.nosql.mapping.document.DocumentTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static jakarta.nosql.document.DocumentQuery.select;

@Named
@ApplicationScoped
public class ListsService {

    Logger logger = Logger.getLogger(ListsService.class.getName());

    @Inject
    @Database(DatabaseType.DOCUMENT)
    private ListsRepository repository;

    @Inject
    private DocumentTemplate template;

    public List<GroceryList> getLists() {
        return repository.findAll();
    }

    public void setLists(List<GroceryList> lists) {
        repository.save(lists);
       // template.update(lists);
    }

    public void saveList(GroceryList list) {
        repository.save(list);
        logger.severe("Attempting to save list " + list);
       // template.update(list);
    }

    public void saveItem(GroceryItem item, String listId) {
        logger.severe("Trying to save item " + item + " to list of id " + listId);
        findList(listId).ifPresent(list -> {
            logger.severe("List found " + list);
            AtomicBoolean wasItemFound = new AtomicBoolean(false);
            List<GroceryItem> items = list.getItems().stream().map(i -> {
                if (Objects.equals(i.getId(), item.getId())) {
                    wasItemFound.set(true);
                    return item;
                } else {
                    return i;
                }
            }).collect(Collectors.toList());
            if (!wasItemFound.get()) {
                logger.severe("Item wasn't found.");
                items.add(item);
            }
            list.setItems(items);
            repository.save(list);
        });
    }

    public Optional<GroceryList> findList(String id) {
        return repository.findById(id);
       // DocumentQuery query = select().from("GroceryList").where("id").eq(id).build();
       // return template.singleResult(query);
    }

    /*
    public GroceryItem findItem(String id) {
        for (GroceryList list : repository.findAll()) {
            for (GroceryItem item : list.getItems()) {
                if (Objects.equals(item.getId(), id)) return item;
            }
        }
        return null;
    }

     */

    public void removeList(String id) {
        repository.deleteById(id);
    }

    public void removeItem(String id) {
        for (GroceryList list : repository.findAll()) {
            list.setItems(
                    list.getItems().stream().filter(item ->
                            !Objects.equals(item.getId(), id)).collect(Collectors.toList())
            );
            repository.save(list);
        }
    }
}
