package piotr.michalkiewicz.mealplannerclient.user.model;

import piotr.michalkiewicz.mealplannerclient.user.model.enums.ActivityLevel;
import piotr.michalkiewicz.mealplannerclient.user.model.enums.Sex;

public class NutritionProfileSettings {

    private Sex sex;
    private int height;
    private int weight;
    private int age;
    private int goal;
    private ActivityLevel activityLevel;

    public static NutritionProfileSettings createMockNutritionProfileSettings(){
        NutritionProfileSettings settings = new NutritionProfileSettings();

        settings.setSex(Sex.MALE);
        settings.setHeight(178);
        settings.setWeight(75);
        settings.setAge(30);
        settings.setGoal(80);
        settings.setActivityLevel(ActivityLevel.MODERATLY_ACTIVE);

        return settings;
    }

    public NutritionProfileSettings() {
    }

    public NutritionProfileSettings(Sex sex, int height, int weight, int age, int goal, ActivityLevel activityLevel) {
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.goal = goal;
        this.activityLevel = activityLevel;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }
}
