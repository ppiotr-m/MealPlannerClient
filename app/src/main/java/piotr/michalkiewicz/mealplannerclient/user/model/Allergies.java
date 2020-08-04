package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Allergies implements Serializable {

    private boolean gluten;
    private boolean dairy;
    private boolean peanuts;
    private boolean treeNuts;
    private boolean soy;
    private boolean egs;
    private boolean fruttiDiMare;
    private boolean sesame;
}
