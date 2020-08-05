package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAccount implements Serializable {

    private String id;
    private String email;
    private String name;
    private String username;
    private String password;
    private UserSettings userSettings;
}
