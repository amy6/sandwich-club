package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.sandwichclub.MainActivity.LOG_TAG;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        //declare a sandwich object
        Sandwich sandwich = null;

        try {
            //get the root sandwich object from json string
            JSONObject sandwichObj = new JSONObject(json);
            //fetch the name
            JSONObject nameObject = sandwichObj.getJSONObject("name");

            //parse the name and alsoKnownAs list from the name object
            String mainName = nameObject.getString("mainName");
            JSONArray alsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }

            //parse the place of origin, description and imageUrl
            String placeOfOrigin = sandwichObj.getString("placeOfOrigin");
            String description = sandwichObj.getString("description");
            String imageUrl = sandwichObj.getString("image");

            //parse the ingredients list
            JSONArray ingredientsArray = sandwichObj.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to parse the json data" + "\n" + e.getMessage());
        }
        return sandwich;
    }
}
