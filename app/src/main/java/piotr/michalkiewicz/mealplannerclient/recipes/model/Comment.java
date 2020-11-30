package piotr.michalkiewicz.mealplannerclient.recipes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comment implements Serializable {

    private final String username;
    private final String comment;

    public static List<Comment> createMockCommentList() {
        ArrayList<Comment> result = new ArrayList<>();

        result.add(new Comment("Andrzej Trąbka", "Na zdjęciu ten sos wygląda jakby warzywa były przetarte, Właśnie mi się duszą policzki z warzywami, czy godzina wystarczy?"));
        result.add(new Comment("Barbara Dwukan", "Robię już któryś raz . Wyśmienite , proste danie . Mięso delikatne ,z grzybami komponuje się idealnie . Wszystkim smakowało mimo , że nie jestem biegła w gotowaniu\n" +
                "Pozdrawiam i dziękuję za przepis"));
        result.add(new Comment("Dariusz Pierun", "Jak zwykle wszystko wyśmienite"));
        result.add(new Comment("Magdalena Wengorz", "Zapiekanka pierwsza klasa. Do tej pory żadna mi nie wychodziła, więc unikałam jej ognia, a tu prosze takie pyszne zaskoczenie polecam i to bardzo! Przepis bardzo precyzyjnie przygotowany!Pani Aniu"));
        result.add(new Comment("Wojciech Petarda", "Papryka chyba jednak lepsze, pomidor się rozpadnie4.nie próbowałam, ale chyba można, zapiekanka jednak będzie się bardziej rozwalać podczas krojenia5.inaczej nigdy nie robiłam,mieszanka daje smak i fajny kolor po zapieczeniu6.tak, może być dowolny, mielone tez będzie ok7.tak, mozna. A te pyszności to babeczki vel miękkie ciastka czekoladowe z czekoladą w środku:) Pokazać je? Ufff, odpowiedziałam na wszystko :P PS. Dzięki:) Kwiaty z pracy, a ku"));

        return result;
    }

    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
}
