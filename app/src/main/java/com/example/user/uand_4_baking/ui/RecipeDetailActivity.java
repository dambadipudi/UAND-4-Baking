package com.example.user.uand_4_baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.model.Step;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener{

    private static String CLICKED_RECIPE_OBJECT = "RECIPE_DETAILS";

    private RecipeDetailFragment detailFrag;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe_detail);

            Intent intent = getIntent();
            if (intent.hasExtra(CLICKED_RECIPE_OBJECT)) {
                final Recipe recipe = intent.getParcelableExtra(CLICKED_RECIPE_OBJECT);

                detailFrag = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_detail_fragment);
                detailFrag.setRecipeData(recipe);

                setTitle(recipe.getName());
            }
        }

    @Override
    public void onStepClicked(Step clickedStep) {
        Toast.makeText(this, "SHOW THE STEP DETAILS IN A NEW DYNAMIC FRAGMENT", Toast.LENGTH_LONG).show();
    }
}
