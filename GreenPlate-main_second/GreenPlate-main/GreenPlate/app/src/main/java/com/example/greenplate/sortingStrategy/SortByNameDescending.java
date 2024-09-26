package com.example.greenplate.sortingStrategy;

import com.example.greenplate.database.Recipe;
import java.util.Collections;
import java.util.List;

public class SortByNameDescending implements SortingStrategy {
    @Override
    public void sort(List<Recipe> list) {
        Collections.sort(list, (r1, r2) -> r2.getName().compareTo(r1.getName()));
    }
}
