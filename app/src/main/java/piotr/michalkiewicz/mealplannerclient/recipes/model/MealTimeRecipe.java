package piotr.michalkiewicz.mealplannerclient.recipes.model;

import com.google.gson.annotations.SerializedName;

import org.bson.types.Binary;

import java.util.Date;
import java.util.List;

import lombok.Data;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Diet;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Level;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.RecipeCuisine;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.RecipeType;


@Data
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

    @SerializedName("image")
    private Binary image;

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
