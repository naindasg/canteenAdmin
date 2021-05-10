package com.example.canteenadmin;

public class Meal {
    private String mealID;
    private String name;
    private String description;
    private String ingredients;
    private String price;
    private String url;

    public Meal(String mealID, String name, String description, String ingredients, String price, String url) {
        this.mealID = mealID;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
        this.url = url;
    }

    public Meal() {
    }

    public String getMealID() {
        return mealID;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
