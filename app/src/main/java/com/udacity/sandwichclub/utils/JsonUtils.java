package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    /** This method parses the json data passed in string format
     *
     * @param json contains the sandwich data in json format
     * @return sandwich object after parsing json data in simpler format.
     */


    public static Sandwich parseSandwichJson(String json) {

        /**
         * If the JSON string is empty or null, then return early
         */
        if(TextUtils.isEmpty(json)){
            return null;
        }

        /**
         * Variables to be used for retrieving data from json String
         */
        final String SANDWICH_NAME = "name";
        final String SANDWICH_MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE_LINK = "image";
        final String INGREDIENTS = "ingredients";

        /**
         * Variables to store retrieved data for Sandwich object
         */

          String mainName;
          List<String> alsoKnownAs= new ArrayList<>();
          String placeOfOrigin;
          String description;
          List<String> ingredients= new ArrayList<>();
          String image;

        try {
            JSONObject sandwichDetails = new JSONObject(json);

            JSONObject name= sandwichDetails.optJSONObject(SANDWICH_NAME);
                mainName = name.optString(SANDWICH_MAIN_NAME);

                JSONArray arrayKnownAs = name.optJSONArray(ALSO_KNOWN_AS);
                    if(arrayKnownAs!=null && arrayKnownAs.length()!=0){
                        for(int i=0;i<arrayKnownAs.length();i++) {
                            alsoKnownAs.add(arrayKnownAs.getString(i));
                        }
                    }else {
                        alsoKnownAs.add("");
                    }

               placeOfOrigin = sandwichDetails.optString(PLACE_OF_ORIGIN);
               description = sandwichDetails.optString(DESCRIPTION);
               image = sandwichDetails.optString(IMAGE_LINK);
               JSONArray arrayIngredients = sandwichDetails.optJSONArray(INGREDIENTS);
               if(arrayIngredients!= null && arrayIngredients.length()>0){
                   for(int i=0;i<arrayIngredients.length();i++){
                       ingredients.add(arrayIngredients.getString(i));
                   }
               }else {
                   ingredients.add("");
               }

               return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,
                                    image,ingredients);

        }
        catch(JSONException e){
            Log.e(TAG, "Json Parsing Error "+ e.getMessage());

        }
        return null;
    }
}
