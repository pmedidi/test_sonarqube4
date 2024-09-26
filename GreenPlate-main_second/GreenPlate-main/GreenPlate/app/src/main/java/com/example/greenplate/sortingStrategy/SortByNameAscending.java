package com.example.greenplate.sortingStrategy;

import com.example.greenplate.database.Recipe;
import java.util.Collections;
import java.util.List;

public class SortByNameAscending implements SortingStrategy {
    @Override
    public void sort(List<Recipe> list) {
        Collections.sort(list, (r1, r2) -> r1.getName().compareTo(r2.getName()));
    }
}
