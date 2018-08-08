package com.example.user.uand_4_baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener{

    private static String CLICKED_RECIPE_OBJECT = "RECIPE_DETAILS";

    private static String CLICKED_STEP_LIST = "STEP_LIST";

    private static String CLICKED_STEP_POSITION = "STEP_POSITION";

    private RecipeDetailFragment detailFrag;

    private boolean mTwoPane;

    StepDetailFragment mCurrentStepFragment;

    private static String CURRENT_STEP_POSITION = "CURRENT_STEP_POSITION";

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

                if(findViewById(R.id.ll_step_detail) != null) {
                    mTwoPane = true;

                    mCurrentStepFragment = new StepDetailFragment();

                    int position = 0;

                    if(savedInstanceState != null && savedInstanceState.containsKey(CURRENT_STEP_POSITION)){
                        position = savedInstanceState.getInt(CURRENT_STEP_POSITION);
                    }

                    List<Step> stepList = recipe.getSteps();

                    mCurrentStepFragment.setStepData(stepList, position);

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .add(R.id.step_detail_container, mCurrentStepFragment)
                            .commit();
                } else {
                    mTwoPane = false;
                }
            }


        }

    @Override
    public void onStepClicked(ArrayList<Step> stepList, int position) {
        if(mTwoPane) {
            mCurrentStepFragment.updatePosition(position);
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putParcelableArrayListExtra(CLICKED_STEP_LIST, stepList);
            intent.putExtra(CLICKED_STEP_POSITION, position);
            startActivity(intent);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mTwoPane) {
            outState.putInt(CURRENT_STEP_POSITION, mCurrentStepFragment.getPosition());
        }

    }
}
