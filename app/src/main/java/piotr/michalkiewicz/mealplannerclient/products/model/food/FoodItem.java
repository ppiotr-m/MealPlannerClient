package piotr.michalkiewicz.mealplannerclient.products.model.food;

import java.io.Serializable;

import piotr.michalkiewicz.mealplannerclient.products.model.nutrients.FoodNutrient;

public abstract class FoodItem implements Serializable {
    private String mongoId;
    private int fdcId;
    private String dataType;
    private String description;
    private String publicationDate;
    private String foodClass;
    private  FoodNutrient[] foodNutrients;
}