package com.szymonharabasz.grocerylistmanager;

import com.szymonharabasz.grocerylistmanager.domain.GroceryList;
import jakarta.nosql.mapping.Repository;

import java.util.List;

public interface ListsRepository extends Repository<GroceryList, String> {
    List<GroceryList> findAll();
}
