package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeIngredient implements Serializable {

    private String amount;
    private String unit;
    private String originalName;
    private String kind;
    private String name;
}
