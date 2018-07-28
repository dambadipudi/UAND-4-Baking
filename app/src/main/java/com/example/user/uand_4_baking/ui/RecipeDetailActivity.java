package com.example.user.uand_4_baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.model.Step;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener{

    private static String CLICKED_RECIPE_OBJECT = "RECIPE_DETAILS";

    private static String CLICKED_STEP_LIST = "STEP_LIST";

    private static String CLICKED_STEP_POSITION = "STEP_POSITION";

    private RecipeDetailFragment detailFrag;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe_detail);

            Intent intent = getIntent();
            if (intent.hasExtra(CLICKED_RECIPE_OBJECT)) {
                Recipe recipe = intent.getParcelableExtra(CLICKED_RECIPE_OBJECT);

                detailFrag = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_detail_fragment);
                detailFrag.setRecipeData(recipe);

                setTitle(recipe.getName());
            }
        }

    @Override
    public void onStepClicked(ArrayList<Step> stepList, int position) {
        Intent intent = new Intent(this, StepDetailActivity.class);
        Log.d("RECIPE_DETAIL_ACTIVITY",stepList.toString());
        intent.putParcelableArrayListExtra(CLICKED_STEP_LIST, stepList);
        Log.d("RECIPE_DETAIL_ACTIVITY",""+position);
        intent.putExtra(CLICKED_STEP_POSITION, position);
        startActivity(intent);
    }
}
