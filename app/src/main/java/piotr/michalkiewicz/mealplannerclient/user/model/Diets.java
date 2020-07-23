package piotr.michalkiewicz.mealplannerclient.user.model;

public class Diets {

    private boolean standard;
    private boolean vegetarian;
    private boolean paleo;
    private boolean flexitarian;
    private boolean vegan;
    private boolean lowCarb;
    private boolean ketogenic;

    public static Diets initDiets() {
        Diets diets = new Diets();
        diets.flexitarian = false;
        diets.ketogenic = false;
        diets.lowCarb = false;
        diets.paleo = false;
        diets.standard = false;
        diets.vegan = false;
        diets.vegetarian = false;

        return diets;
    }

    public Diets() {
    }

    public Diets(boolean standard, boolean vegetarian, boolean paleo, boolean flexitarian, boolean vegan, boolean lowCarb, boolean ketogenic) {
        this.standard = standard;
        this.vegetarian = vegetarian;
        this.paleo = paleo;
        this.flexitarian = flexitarian;
        this.vegan = vegan;
        this.lowCarb = lowCarb;
        this.ketogenic = ketogenic;
    }

    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isPaleo() {
        return paleo;
    }

    public void setPaleo(boolean paleo) {
        this.paleo = paleo;
    }

    public boolean isFlexitarian() {
        return flexitarian;
    }

    public void setFlexitarian(boolean flexitarian) {
        this.flexitarian = flexitarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isLowCarb() {
        return lowCarb;
    }

    public void setLowCarb(boolean lowCarb) {
        this.lowCarb = lowCarb;
    }

    public boolean isKetogenic() {
        return ketogenic;
    }

    public void setKetogenic(boolean ketogenic) {
        this.ketogenic = ketogenic;
    }
}
