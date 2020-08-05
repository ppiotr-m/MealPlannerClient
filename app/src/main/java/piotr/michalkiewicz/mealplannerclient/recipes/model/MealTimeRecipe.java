package piotr.michalkiewicz.mealplannerclient.recipes.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
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
    private double cookTime;
    @SerializedName("totalRating")
    private double totalRating;
    @SerializedName("views")
    private long views;
    @SerializedName("totalLikes")
    private long totalLikes;
}
