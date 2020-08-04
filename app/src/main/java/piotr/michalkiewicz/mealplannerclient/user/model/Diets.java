package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Diets implements Serializable {

    private boolean standard;
    private boolean vegetarian;
    private boolean paleo;
    private boolean flexitarian;
    private boolean vegan;
    private boolean lowCarb;
    private boolean ketogenic;
}
