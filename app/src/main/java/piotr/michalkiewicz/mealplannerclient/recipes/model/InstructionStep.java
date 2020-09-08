package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;
import java.util.List;

public class InstructionStep implements Serializable {

    private int stepNumber;
    private String stepInstruction;
    private List<String> recipeIngredients;

    public int getStepNumber() {
        return stepNumber;
    }

    public String getStepInstruction() {
        return stepInstruction;
    }

    public List<String> getRecipeIngredients() {
        return recipeIngredients;
    }
}
