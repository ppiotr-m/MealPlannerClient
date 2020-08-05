package piotr.michalkiewicz.mealplannerclient.recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment {

    private String username;
    private String comment;
}
