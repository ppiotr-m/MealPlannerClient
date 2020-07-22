package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;

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

    public RecipeIngredient() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
