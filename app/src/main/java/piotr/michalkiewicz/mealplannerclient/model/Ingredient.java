package piotr.michalkiewicz.mealplannerclient.model;

public class Ingredient {
    String ingredientName;

    public Ingredient(String temp) {
        this.ingredientName = temp;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String temp) {
        this.ingredientName = temp;
    }
}
