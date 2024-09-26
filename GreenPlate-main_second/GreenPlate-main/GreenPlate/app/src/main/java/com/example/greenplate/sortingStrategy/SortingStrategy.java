package com.example.greenplate.sortingStrategy;

import com.example.greenplate.database.Recipe;
import java.util.List;

public interface SortingStrategy {
    void sort(List<Recipe> list);
}
