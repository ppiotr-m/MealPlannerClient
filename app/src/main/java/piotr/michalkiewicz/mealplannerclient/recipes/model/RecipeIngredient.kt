package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;
import java.util.Objects;

public class RecipeIngredient implements Serializable {

    private String amount;
    private String unit;
    private String originalName;
    private String kind;
    private String name;

    public RecipeIngredient(String amount, String unit, String originalName, String kind, String name) {
        this.amount = amount;
        this.unit = unit;
        this.originalName = originalName;
        this.kind = kind;
        this.name = name;
    }

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

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }
}
