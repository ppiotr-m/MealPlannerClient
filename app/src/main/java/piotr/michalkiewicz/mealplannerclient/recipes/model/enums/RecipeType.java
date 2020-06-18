package piotr.michalkiewicz.mealplannerclient.recipes.model.enums;

public enum RecipeType {
    SOUP("soup"),
    FISH("fish"),
    VEGETARIAN("vegetarian"),
    MEAT("meat"),
    DAIRY("dairy");

    private String value;

    RecipeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

