package piotr.michalkiewicz.mealplannerclient.model;

import java.util.List;

public class RecipeInstructions {

    int stepNumber;

    /**
     * Text values are best if the elements in the list are plain strings. Existing entities are best for a simple, unordered list of existing things in your data.
     * ListItem is used with ordered lists when you want to provide additional context about the element in that list or when the same item might be in different places in different lists.
     */

    List<String> itemListElement;

    /**
     * things to make in step
     */

    String stepInstruction;

    /**
     * Ingredients list
     */

    List<String> recipeIngredients;
}
