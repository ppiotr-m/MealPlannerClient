package piotr.michalkiewicz.mealplannerclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MealTimeRecipe {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("cookTime")
    private double cookTime;

    @SerializedName("recipeTag")
    private List<String> recipeTag;

    @SerializedName("recipeType")
    private List<String> recipeType;

    @SerializedName("level")
    private String level;

    @SerializedName("recipeIngredients")
    private List<RecipeIngredient> recipeIngredients;

    @SerializedName("description")
    private String description;

    @SerializedName("instructionSteps")
    private List<InstructionStep> instructionSteps;

    @SerializedName("recipeYield")
    private String recipeYield;

    @SerializedName("suitableForDiet")
    private List<String> suitableForDiet;

    @SerializedName("from")
    private String from;

    @SerializedName("madyBY")
    private String madeBY;

    @SerializedName("language")
    private String language;

    public MealTimeRecipe(String id, String name, double cookTime, List<String> recipeTag, List<String> recipeType,
                          String level, List<RecipeIngredient> recipeIngredients, String description,
                          List<InstructionStep> instructionSteps, String recipeYield, List<String> suitableForDiet,
                          String from, String madeBY, String language) {
        this.id = id;
        this.name = name;
        this.cookTime = cookTime;
        this.recipeTag = recipeTag;
        this.recipeType = recipeType;
        this.level = level;
        this.recipeIngredients = recipeIngredients;
        this.description = description;
        this.instructionSteps = instructionSteps;
        this.recipeYield = recipeYield;
        this.suitableForDiet = suitableForDiet;
        this.from = from;
        this.madeBY = madeBY;
        this.language = language;
    }

    public MealTimeRecipe(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCookTime() {
        return cookTime;
    }

    public void setCookTime(double cookTime) {
        this.cookTime = cookTime;
    }

    public List<String> getRecipeTag() {
        return recipeTag;
    }

    public void setRecipeTag(List<String> recipeTag) {
        this.recipeTag = recipeTag;
    }

    public List<String> getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(List<String> recipeType) {
        this.recipeType = recipeType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InstructionStep> getInstructionSteps() {
        return instructionSteps;
    }

    public void setInstructionSteps(List<InstructionStep> instructionSteps) {
        this.instructionSteps = instructionSteps;
    }

    public String getRecipeYield() {
        return recipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        this.recipeYield = recipeYield;
    }

    public List<String> getSuitableForDiet() {
        return suitableForDiet;
    }

    public void setSuitableForDiet(List<String> suitableForDiet) {
        this.suitableForDiet = suitableForDiet;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMadeBY() {
        return madeBY;
    }

    public void setMadeBY(String madeBY) {
        this.madeBY = madeBY;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
