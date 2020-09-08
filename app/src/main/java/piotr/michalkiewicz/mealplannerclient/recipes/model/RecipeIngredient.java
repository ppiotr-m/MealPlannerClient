package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return originalName.equals(that.originalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalName);
    }
}
