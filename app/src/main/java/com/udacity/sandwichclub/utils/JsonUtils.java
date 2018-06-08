package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;


import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;


import java.util.ArrayList;


public class JsonUtils {


    static ArrayList<String> alsoKnownAsList;

    static ArrayList<String> ingredientsList;


    public static Sandwich parseSandwichJson(String json) {

        final String KEY_NAME = "name";
        final String KEY_MAIN_NAME = "mainName";
        final String KEY_ALSO_KNOWNN_AS = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_INGREDIENTS = "ingredients";

        int arrayMark=0;
        Sandwich sandwich=null;
        try {
            //Creating an object for json file
            JSONObject jsonObject = new JSONObject(json);
            //obtain name from the first root object
            JSONObject nameObject = jsonObject.getJSONObject(KEY_NAME);
            String mainName = nameObject.getString(KEY_MAIN_NAME);
            JSONArray alsoKnownAsArray = nameObject.getJSONArray(KEY_ALSO_KNOWNN_AS);
            alsoKnownAsList = new ArrayList<>();
            //Add items to alsoKnownAs list from Json array
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsArray.getString(i));
            }
            //getting the info from json file using json object
            String placeOfOrigin = jsonObject.getString(KEY_PLACE_OF_ORIGIN);
            String description = jsonObject.getString(KEY_DESCRIPTION);
            String imageUrl = jsonObject.getString(KEY_IMAGE);
            JSONArray ingredientsArray = jsonObject.getJSONArray(KEY_INGREDIENTS);
            ingredientsList = new ArrayList<>();

            //Add items to the Ingredients List from Json array
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }
            //Creating an object to Sandwich class
            sandwich = new Sandwich(mainName,alsoKnownAsList,placeOfOrigin,description,imageUrl,ingredientsList);

            return sandwich;

        }
        //catch method in case of any exception
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}