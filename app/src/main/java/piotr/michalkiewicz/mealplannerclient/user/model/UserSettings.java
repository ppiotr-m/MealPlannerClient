package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSettings implements Serializable {

    private RecipeTypesSetting recipeTypesSetting;
    private NutritionProfileSettings nutritionProfileSettings;
    private Allergies allergies;
    private Diets diets;
    private List<String> unlikeIngredients;
    private List<String> eatenRecipes;
    private String Language;
    private int portionPreferences;
    private int cookingTimePreference;
    private int mealsPerMealPlanPreference;
}
