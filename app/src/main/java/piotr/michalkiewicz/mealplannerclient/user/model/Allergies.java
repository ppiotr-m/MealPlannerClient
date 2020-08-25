package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

public class Allergies implements Serializable {

    private boolean gluten;
    private boolean dairy;
    private boolean peanuts;
    private boolean treeNuts;
    private boolean soy;
    private boolean egs;
    private boolean fruttiDiMare;
    private boolean sesame;

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
