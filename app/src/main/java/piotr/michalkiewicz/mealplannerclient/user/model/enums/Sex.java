package piotr.michalkiewicz.mealplannerclient.user.model.enums;

public enum Sex {
    MALE ("Male"),
    FEMALE ("Female");

    Sex(String name){
        value = name;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
