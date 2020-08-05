package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstructionStep implements Serializable {

    private int stepNumber;
    private String stepInstruction;
    private List<String> recipeIngredients;
}
