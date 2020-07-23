package piotr.michalkiewicz.mealplannerclient.user.model.enums;

import java.io.Serializable;

public enum ActivityLevel implements Serializable {
    SEDENTARY("Sedentary"),
    LIGHTLY_ACTIVE ("LightlyActive"),
    MODERATELY_ACTIVE("ModeratelyActive"),
    VERY_ACTIVE ("VeryActive");

    ActivityLevel(String name){
        value = name;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
