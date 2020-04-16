package piotr.michalkiewicz.mealplannerclient.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.model.FoodTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.support.ViewUpdater;

public class HttpConnectionHandler {

    public final String urlString = "http://192.168.1.10:8080/recipes/random?amount=10";
    private ViewUpdater view;

    public ViewUpdater getView() {
        return view;
    }

    public void setView(ViewUpdater view) {
        this.view = view;
    }

    public void getRandomRecipes(){
        GetRecipesAsyncTask task = new GetRecipesAsyncTask();
        task.execute();
    }


    private String readStream(InputStream inputStream){
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8);
            return writer.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private FoodTimeRecipe [] getRecipesFromString(String jsonString){

        Gson gson = new Gson();
        Log.d(Constants.TAG, "JSON STRING\n"+jsonString);
        FoodTimeRecipe [] recipes = gson.fromJson(jsonString, FoodTimeRecipe[].class);
        return recipes;
    }

    class GetRecipesAsyncTask extends AsyncTask<Void, Void, FoodTimeRecipe[]>{
        @Override
        protected FoodTimeRecipe[] doInBackground(Void... voids) {
            try {
                URL url = new URL(urlString);
                Log.d(Constants.TAG, "Otwieranie polaczenia:");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    Log.d(Constants.TAG, "Przed pobraniem");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    return getRecipesFromString(readStream(in));
                } finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(FoodTimeRecipe[] foodTimeRecipes) {
            super.onPostExecute(foodTimeRecipes);
            Log.d(Constants.TAG, "On post execute");
            if (foodTimeRecipes != null) {
                Log.d(Constants.TAG, "Passing data to view controller");
                view.updateView(foodTimeRecipes);
            }
        }
    }

}
