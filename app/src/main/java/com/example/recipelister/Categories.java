package com.example.recipelister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Categories {
    ArrayList<Map<String, String>> categories;

    public Categories(){

    }

    public Categories(ArrayList<Map<String, String>> categories) {
        this.categories = categories;
    }

    public ArrayList<Map<String, String>> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Map<String, String>> categories) {
        this.categories = categories;
    }
}
