package piotr.michalkiewicz.mealplannerclient.products.model.food;

import piotr.michalkiewicz.mealplannerclient.products.model.extra.FoodCategory;

public class SRLegacyFoodItem extends FoodItem {
    private boolean isHistoricalReference;
    private String ndbNumber;
    private String scientificName;
    private FoodCategory foodCategory;
 //   private NutrientConversionFactors[] nutrientConversionFactors;
}