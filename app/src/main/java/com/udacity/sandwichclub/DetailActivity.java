package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView mSandwichImageView;
    TextView mMainName;
    TextView mAlsoKnownAs;
    TextView mPlaceOfOrigin;
    TextView mDescription;
    TextView mIngredients;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         * get reference of all variables from the UI to
         * populate them with data received from parsing json string
         */
        mSandwichImageView = findViewById(R.id.sandwichImageView);
        mMainName = findViewById(R.id.mainNameTextView);
        mAlsoKnownAs =  findViewById(R.id.knownAsTextView);
        mPlaceOfOrigin = findViewById(R.id.placeOfOriginTextView);
        mDescription = findViewById(R.id.descriptionTextView);
        mIngredients = findViewById(R.id.ingredientsTextView);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mSandwichImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method populates the UI values with the data from the model Sandwich class
     */
    private void populateUI(Sandwich sandwich) {

        //set mainName
        mMainName.setText(sandwich.getMainName());

        //set alsoKnownAs data
        List<String> data =sandwich.getAlsoKnownAs();
        if(data!=null){
            for(String s : data) {
                mAlsoKnownAs.append(s+", ");
            }
        }else {
            mAlsoKnownAs.setText(" ");
        }

        //set placeOfOrigin Data
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());

        //set description
        mDescription.setText(sandwich.getDescription());

        //set Ingredients
        List<String> ingredientsData = sandwich.getIngredients();
        if(ingredientsData!=null) {
            for (String info : ingredientsData) {
                mIngredients.append(info + ", ");
            }
        }else
            mIngredients.setText(" ");


    }
}
