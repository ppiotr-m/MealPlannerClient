package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;
import java.util.List;

public class InstructionStep implements Serializable {

    private int stepNumber;
    private String stepInstruction;
    private List<String> recipeIngredients;

    public InstructionStep(int stepNumber, String stepInstruction, List<String> recipeIngredients) {
        this.stepNumber = stepNumber;
        this.stepInstruction = stepInstruction;
        this.recipeIngredients = recipeIngredients;
    }

    public InstructionStep() {
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getStepInstruction() {
        return stepInstruction;
    }

    public void setStepInstruction(String stepInstruction) {
        this.stepInstruction = stepInstruction;
    }

    public List<String> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<String> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
