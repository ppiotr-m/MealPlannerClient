package piotr.michalkiewicz.mealplannerclient.user.model;

public class Allergies {

    private boolean gluten;
    private boolean dairy;
    private boolean peanuts;
    private boolean treeNuts;
    private boolean soy;
    private boolean egs;
    private boolean fruttiDiMare;
    private boolean sesame;

    public static Allergies initAllergies() {
        Allergies allergies = new Allergies();
        allergies.dairy = false;
        allergies.egs = false;
        allergies.fruttiDiMare = false;
        allergies.gluten = false;
        allergies.peanuts = false;
        allergies.sesame = false;
        allergies.soy = false;
        allergies.treeNuts = false;

        return allergies;
    }

    public Allergies() {
    }

    public Allergies(boolean gluten, boolean dairy, boolean peanuts, boolean treeNuts, boolean soy,
                     boolean egs, boolean fruttiDiMare, boolean sesame) {
        this.gluten = gluten;
        this.dairy = dairy;
        this.peanuts = peanuts;
        this.treeNuts = treeNuts;
        this.soy = soy;
        this.egs = egs;
        this.fruttiDiMare = fruttiDiMare;
        this.sesame = sesame;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public boolean isDairy() {
        return dairy;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    public boolean isPeanuts() {
        return peanuts;
    }

    public void setPeanuts(boolean peanuts) {
        this.peanuts = peanuts;
    }

    public boolean isTreeNuts() {
        return treeNuts;
    }

    public void setTreeNuts(boolean treeNuts) {
        this.treeNuts = treeNuts;
    }

    public boolean isSoy() {
        return soy;
    }

    public void setSoy(boolean soy) {
        this.soy = soy;
    }

    public boolean isEgs() {
        return egs;
    }

    public void setEgs(boolean egs) {
        this.egs = egs;
    }

    public boolean isFruttiDiMare() {
        return fruttiDiMare;
    }

    public void setFruttiDiMare(boolean fruttiDiMare) {
        this.fruttiDiMare = fruttiDiMare;
    }

    public boolean isSesame() {
        return sesame;
    }

    public void setSesame(boolean sesame) {
        this.sesame = sesame;
    }
}
