package piotr.michalkiewicz.mealplannerclient.recipes.model.enums;

public enum RecipeCuisine {
    POLISH("polish"),
    MEXICAN("mexican"),
    ITALIAN("italian");

    String value;

    RecipeCuisine(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
