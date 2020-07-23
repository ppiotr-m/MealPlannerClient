package piotr.michalkiewicz.mealplannerclient.user.model.enums;

import java.io.Serializable;

public enum Sex implements Serializable {
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
