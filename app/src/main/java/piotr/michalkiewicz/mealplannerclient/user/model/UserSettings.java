package piotr.michalkiewicz.mealplannerclient.user.model;

import java.util.LinkedList;
import java.util.List;

public class UserSettings {

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

    public static UserSettings createMockUserSettings(){
        UserSettings settings = new UserSettings();

        settings.setRecipeTypesSetting(RecipeTypesSetting.initRecipeTypes());
        settings.setNutritionProfileSettings(NutritionProfileSettings.createMockNutritionProfileSettings());
        settings.setAllergies(Allergies.initAllergies());
        settings.setDiets(Diets.initDiets());
        settings.setUnlikeIngredients(new LinkedList<>());
        settings.setEatenRecipes(new LinkedList<>());
        settings.setLanguage("Polish");
        settings.setPortionPreferences(4);
        settings.setCookingTimePreference(60);
        settings.setMealsPerMealPlanPreference(5);

        return settings;
    }

    public UserSettings() {
    }

    public UserSettings(RecipeTypesSetting recipeTypesSetting, NutritionProfileSettings nutritionProfileSettings,
                        Allergies allergies, Diets diets, List<String> unlikeIngredients,
                        List<String> eatenRecipes, String language, int portionPreferences,
                        int cookingTimePreference, int mealsPerMealPlanPreference) {
        this.recipeTypesSetting = recipeTypesSetting;
        this.nutritionProfileSettings = nutritionProfileSettings;
        this.allergies = allergies;
        this.diets = diets;
        this.unlikeIngredients = unlikeIngredients;
        this.eatenRecipes = eatenRecipes;
        this.portionPreferences = portionPreferences;
        this.cookingTimePreference = cookingTimePreference;
        this.mealsPerMealPlanPreference = mealsPerMealPlanPreference;
    }

    public RecipeTypesSetting getRecipeTypesSetting() {
        return recipeTypesSetting;
    }

    public void setRecipeTypesSetting(RecipeTypesSetting recipeTypesSetting) {
        this.recipeTypesSetting = recipeTypesSetting;
    }

    public NutritionProfileSettings getNutritionProfileSettings() {
        return nutritionProfileSettings;
    }

    public void setNutritionProfileSettings(NutritionProfileSettings nutritionProfileSettings) {
        this.nutritionProfileSettings = nutritionProfileSettings;
    }

    public Allergies getAllergies() {
        return allergies;
    }

    public void setAllergies(Allergies allergies) {
        this.allergies = allergies;
    }

    public Diets getDiets() {
        return diets;
    }

    public void setDiets(Diets diets) {
        this.diets = diets;
    }

    public List<String> getUnlikeIngredients() {
        return unlikeIngredients;
    }

    public void setUnlikeIngredients(List<String> unlikeIngredients) {
        this.unlikeIngredients = unlikeIngredients;
    }

    public List<String> getEatenRecipes() {
        return eatenRecipes;
    }

    public void setEatenRecipes(List<String> eatenRecipes) {
        this.eatenRecipes = eatenRecipes;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getPortionPreferences() {
        return portionPreferences;
    }

    public void setPortionPreferences(int portionPreferences) {
        this.portionPreferences = portionPreferences;
    }

    public int getCookingTimePreference() {
        return cookingTimePreference;
    }

    public void setCookingTimePreference(int cookingTimePreference) {
        this.cookingTimePreference = cookingTimePreference;
    }

    public int getMealsPerMealPlanPreference() {
        return mealsPerMealPlanPreference;
    }

    public void setMealsPerMealPlanPreference(int mealsPerMealPlanPreference) {
        this.mealsPerMealPlanPreference = mealsPerMealPlanPreference;
    }
}
