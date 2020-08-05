package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import piotr.michalkiewicz.mealplannerclient.user.model.enums.ActivityLevel;
import piotr.michalkiewicz.mealplannerclient.user.model.enums.Sex;

@Data
@NoArgsConstructor
public class NutritionProfileSettings implements Serializable {

    private Sex sex;
    private int height;
    private int weight;
    private int age;
    private int goal;
    private ActivityLevel activityLevel;
}
