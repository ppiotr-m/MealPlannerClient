package piotr.michalkiewicz.mealplannerclient.recipes.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MealTimeRecipe {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("recipeType")
    private List<String> recipeType;

 //   @SerializedName("recipeIngredients")
 //   private List<FoodItem> recipeIngredients;

    @SerializedName("recipeIngredients")
    private List<RecipeIngredient> recipeIngredients;

    @SerializedName("instructionSteps")
    private List<InstructionStep> instructionSteps;

    @SerializedName("level")
    private String level;

    @SerializedName("suitableForDiet")
    private List<String> suitableForDiet;

    @SerializedName("recipeCuisines")
    private List<String> recipeCuisines;

    @SerializedName("image")
    private Bitmap image;

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
    private int cookTime;
    @SerializedName("totalRating")
    private double totalRating;
    @SerializedName("views")
    private long views;
    @SerializedName("totalLikes")
    private long totalLikes;

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

    public List<String> getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(List<String> recipeType) {
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getSuitableForDiet() {
        return suitableForDiet;
    }

    public void setSuitableForDiet(List<String> suitableForDiet) {
        this.suitableForDiet = suitableForDiet;
    }

    public List<String> getRecipeCuisines() {
        return recipeCuisines;
    }

    public void setRecipeCuisines(List<String> recipeCuisines) {
        this.recipeCuisines = recipeCuisines;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

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

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
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

    @Override
    public String toString() {
        return "MealTimeRecipe{" +
                "id='" + id + "\n" +
                ", name='" + name + "\n" +
                ", recipeType=" + recipeType + "\n" +
                ", recipeIngredients=" + recipeIngredients + "\n" +
                ", instructionSteps=" + instructionSteps + "\n" +
                ", level='" + level + "\n" +
                ", suitableForDiet=" + suitableForDiet + "\n" +
                ", recipeCuisines=" + recipeCuisines + "\n" +
                ", image=" + image + "\n" +
                ", description='" + description + "\n" +
                ", recipeYield='" + recipeYield + "\n" +
                ", from='" + from + "\n" +
                ", madeBY='" + madeBY + "\n" +
                ", language='" + language + "\n" +
                ", recipeTag=" + recipeTag + "\n" +
                ", comments=" + comments + "\n" +
                ", dateAdded=" + dateAdded + "\n" +
                ", dateEdited=" + dateEdited + "\n" +
                ", cookTime=" + cookTime + "\n" +
                ", totalRating=" + totalRating + "\n" +
                ", views=" + views + "\n" +
                ", totalLikes=" + totalLikes +
                '}';
    }
}
