package piotr.michalkiewicz.mealplannerclient.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserAccount implements Serializable {

    private String id;
    private String email;
    private String name;
    private String username;
    private String password;
    private Set<Role> roles;
    private UserSettings userSettings;

    public static UserAccount createMockUserAccount(){
        UserAccount account = new UserAccount();

        account.setId("");
        account.setEmail("aaa@bbb.com");
        account.setName("Zenek");
        account.setUsername("Zenon");
        account.setPassword("aaa");
        Set<Role> roles = new HashSet<>();
        Role r = new Role();
        r.setId("a");
        r.setRole("user");
        roles.add(r);
        account.setRoles(roles);
        account.setUserSettings(UserSettings.createMockUserSettings());

        return account;
    }

    public UserAccount() {
    }

    public UserAccount(String id, String email, String name, String username, String password, Set<Role> roles, UserSettings userSettings) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.userSettings = userSettings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }
}
