package piotr.michalkiewicz.mealplannerclient.recipes.model.conversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

public final class CustomGsonConverterCreator {

    private CustomGsonConverterCreator(){};

    public static Converter.Factory createGsonConverter(Type type, Object typeAdapter){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
