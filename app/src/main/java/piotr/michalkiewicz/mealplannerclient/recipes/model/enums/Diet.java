package piotr.michalkiewicz.mealplannerclient.recipes.model.enums;

public enum Diet {
    STANDARD("standard"),
    VEGETARIAN("vegetarian"),
    PALEO("paleo"),
    FLEXITARIAN("flexitarian"),
    VEGAN("vegan"),
    LOWCARB("lowcarb"),
    KETOGENIC("ketogenic");

    private String value;

    Diet(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
