package piotr.michalkiewicz.mealplannerclient.recipes.model.enums;

public enum Level {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    String value;

    Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
