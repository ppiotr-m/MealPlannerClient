package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeTypesSetting implements Serializable {

    private boolean soup;
    private boolean fish;
    private boolean vegetarian;
    private boolean meat;
    private boolean dairy;
}
