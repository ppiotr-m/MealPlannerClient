package piotr.michalkiewicz.mealplannerclient.recipes.model.conversion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import piotr.michalkiewicz.mealplannerclient.support.Constants;

public class BinaryToBitmapConverter implements JsonDeserializer<Bitmap> {

    @Override
    public Bitmap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        byte[] decodedString = Base64.decode(json.getAsString(), Base64.DEFAULT);
        Log.i(Constants.TAG, "JsonElement as string: " + json.toString());
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
