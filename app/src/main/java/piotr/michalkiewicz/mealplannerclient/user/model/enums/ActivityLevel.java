package piotr.michalkiewicz.mealplannerclient.user.model.enums;

public enum ActivityLevel {
    SEDENTERY ("Sedentery"),
    LIGHTLY_ACTIVE ("LightlyActive"),
    MODERATLY_ACTIVE ("ModeratlyActive"),
    VERY_ACTIVE ("VeryActive");

    ActivityLevel(String name){
        value = name;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
