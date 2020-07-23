package piotr.michalkiewicz.mealplannerclient.user.model;

public class RecipeTypesSetting {

    private boolean soup;
    private boolean fish;
    private boolean vegetarian;
    private boolean meat;
    private boolean dairy;

    public static RecipeTypesSetting initRecipeTypes() {
        RecipeTypesSetting recipeTypesSetting = new RecipeTypesSetting();
        recipeTypesSetting.dairy = false;
        recipeTypesSetting.fish = false;
        recipeTypesSetting.meat = false;
        recipeTypesSetting.vegetarian = false;
        recipeTypesSetting.soup = false;

        return recipeTypesSetting;
    }

    public RecipeTypesSetting() {
    }

    public RecipeTypesSetting(boolean soup, boolean fish, boolean vegetarian, boolean meat, boolean dairy) {
        this.soup = soup;
        this.fish = fish;
        this.vegetarian = vegetarian;
        this.meat = meat;
        this.dairy = dairy;
    }

    public boolean isSoup() {
        return soup;
    }

    public void setSoup(boolean soup) {
        this.soup = soup;
    }

    public boolean isFish() {
        return fish;
    }

    public void setFish(boolean fish) {
        this.fish = fish;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isMeat() {
        return meat;
    }

    public void setMeat(boolean meat) {
        this.meat = meat;
    }

    public boolean isDairy() {
        return dairy;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }
}
