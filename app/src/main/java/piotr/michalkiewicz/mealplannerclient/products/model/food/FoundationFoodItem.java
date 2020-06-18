package piotr.michalkiewicz.mealplannerclient.products.model.food;

import java.io.Serializable;

import piotr.michalkiewicz.mealplannerclient.products.model.extra.FoodCategory;

public class FoundationFoodItem extends FoodItem implements Serializable {
    private String footNote;
    private boolean isHistoricalReference;
    private String ndbNumber;
    private String scientificName;
    private FoodCategory foodCategory;
//    private FoodComponent[] foodComponents;
//    private FoodPortion[] foodPortions;
//    InputFoodFoundation[] inputFoods;
//    private NutrientConversionFactors[] nutrientConversionFactors;
}