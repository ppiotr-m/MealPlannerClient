package piotr.michalkiewicz.mealplannerclient.model;

import java.util.List;

public class FoodTimeRecipe {

    String id;

    String name;

    String cookTime;

//    /**
//     * Nutrition information about the recipe or menu item.
//     */

//    NutritionInformation nutritionInformation;


    List<String> recipeCategory;  //todo create ENUMS for most popular categories


    List<String> recipeCuisine;   //todo create ENUMS for most popular cuisines


    List<RecipeIngredient> recipeIngredient;

    String recipeInstruction;

    List<RecipeInstructions> recipeInstructions;


    String recipeYield;

    List<String> suitableForDiet;

    /**
     * FoodTimeRecipe origin
     */

    String from;


    String madeBY;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
