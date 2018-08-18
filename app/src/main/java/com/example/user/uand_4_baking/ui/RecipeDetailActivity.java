package com.example.user.uand_4_baking.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.uand_4_baking.BakingWidget;
import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Ingredient;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.model.Step;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener{

    private boolean mTwoPane;

    public String mRecipeName = "";

    StepDetailFragment mCurrentStepFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe_detail);

            Intent intent = getIntent();
            String CLICKED_RECIPE_OBJECT = "RECIPE_DETAILS";
            if (intent.hasExtra(CLICKED_RECIPE_OBJECT)) {
                Recipe recipe = intent.getParcelableExtra(CLICKED_RECIPE_OBJECT);
                RecipeDetailFragment detailFrag = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_detail_fragment);
                detailFrag.setRecipeData(recipe);

                mRecipeName = recipe.getName();
                setTitle(mRecipeName);

                updateRecentRecipeForWidget(recipe);

                if(findViewById(R.id.ll_step_detail) != null) {
                    mTwoPane = true;

                    List<Step> stepList = recipe.getSteps();

                    if(savedInstanceState == null) {
                        mCurrentStepFragment = new StepDetailFragment();

                        mCurrentStepFragment.setStepData(stepList, 0);

                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .add(R.id.step_detail_container, mCurrentStepFragment)
                                .commit();

                    }

                } else {
                    mTwoPane = false;
                }
            }

        }

    @Override
    public void onStepClicked(ArrayList<Step> stepList, int position) {
        if(mTwoPane) {
            mCurrentStepFragment.resetPlayer();
            mCurrentStepFragment.updatePosition(position);
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            String CLICKED_STEP_LIST = "STEP_LIST";
            intent.putParcelableArrayListExtra(CLICKED_STEP_LIST, stepList);
            String CLICKED_STEP_POSITION = "STEP_POSITION";
            intent.putExtra(CLICKED_STEP_POSITION, position);
            String RECIPE_TITLE = "RECIPE_NAME";
            intent.putExtra(RECIPE_TITLE, mRecipeName);
            startActivity(intent);
        }

    }

    public void updateRecentRecipeForWidget(Recipe recipe) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidget.class));
        BakingWidget.updateSelectedRecipe(this, appWidgetManager, appWidgetIds, recipe);

    }
}
