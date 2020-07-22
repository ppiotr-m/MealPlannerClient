package piotr.michalkiewicz.mealplannerclient.recipes.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.products.model.food.FoodItem;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Diet;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Level;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.RecipeCuisine;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.RecipeType;


public class MealTimeRecipe {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("recipeType")
    private List<RecipeType> recipeType;

 //   @SerializedName("recipeIngredients")
 //   private List<FoodItem> recipeIngredients;

    @SerializedName("recipeIngredients")
    private List<RecipeIngredient> recipeIngredients;

    @SerializedName("instructionSteps")
    private List<InstructionStep> instructionSteps;

    @SerializedName("level")
    private Level level;

    @SerializedName("suitableForDiet")
    private List<Diet> suitableForDiet;

    @SerializedName("recipeCuisines")
    private List<RecipeCuisine> recipeCuisines;

//    @SerializedName("image")
//    private byte[] image;

    @SerializedName("description")
    private String description;
    @SerializedName("recipeYield")
    private String recipeYield;
    @SerializedName("from")
    private String from;
    @SerializedName("madeBY")
    private String madeBY;
    @SerializedName("language")
    private String language;
    @SerializedName("recipeTag")
    private List<String> recipeTag;
    @SerializedName("comments")
    private List<Comment> comments;
    @SerializedName("dateAdded")
    private Date dateAdded;
    @SerializedName("dateEdited")
    private Date dateEdited;
    @SerializedName("cookTime")
    private double cookTime;
    @SerializedName("totalRating")
    private double totalRating;
    @SerializedName("views")
    private long views;
    @SerializedName("totalLikes")
    private long totalLikes;

    public MealTimeRecipe(String id, String name, List<RecipeType> recipeType,
                          List<RecipeIngredient> recipeIngredients, List<InstructionStep> instructionSteps,
                          Level level, List<Diet> suitableForDiet,
                          List<RecipeCuisine> recipeCuisines, String description,
                          String recipeYield, String from, String madeBY, String language,
                          List<String> recipeTag, List<Comment> comments, Date dateAdded,
                          Date dateEdited, double cookTime, double totalRating, long views,
                          long totalLikes) {
        this.id = id;
        this.name = name;
        this.recipeType = recipeType;
        this.recipeIngredients = recipeIngredients;
        this.instructionSteps = instructionSteps;
        this.level = level;
        this.suitableForDiet = suitableForDiet;
        this.recipeCuisines = recipeCuisines;
  //      this.image = image;
        this.description = description;
        this.recipeYield = recipeYield;
        this.from = from;
        this.madeBY = madeBY;
        this.language = language;
        this.recipeTag = recipeTag;
        this.comments = comments;
        this.dateAdded = dateAdded;
        this.dateEdited = dateEdited;
        this.cookTime = cookTime;
        this.totalRating = totalRating;
        this.views = views;
        this.totalLikes = totalLikes;
    }

    public MealTimeRecipe() {
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

    public List<RecipeType> getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(List<RecipeType> recipeType) {
        this.recipeType = recipeType;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<InstructionStep> getInstructionSteps() {
        return instructionSteps;
    }

    public void setInstructionSteps(List<InstructionStep> instructionSteps) {
        this.instructionSteps = instructionSteps;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Diet> getSuitableForDiet() {
        return suitableForDiet;
    }

    public void setSuitableForDiet(List<Diet> suitableForDiet) {
        this.suitableForDiet = suitableForDiet;
    }

    public List<RecipeCuisine> getRecipeCuisines() {
        return recipeCuisines;
    }

    public void setRecipeCuisines(List<RecipeCuisine> recipeCuisines) {
        this.recipeCuisines = recipeCuisines;
    }

  /*  public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
*/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipeYield() {
        return recipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        this.recipeYield = recipeYield;
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

    public List<String> getRecipeTag() {
        return recipeTag;
    }

    public void setRecipeTag(List<String> recipeTag) {
        this.recipeTag = recipeTag;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    public double getCookTime() {
        return cookTime;
    }

    public void setCookTime(double cookTime) {
        this.cookTime = cookTime;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(long totalLikes) {
        this.totalLikes = totalLikes;
    }
}
