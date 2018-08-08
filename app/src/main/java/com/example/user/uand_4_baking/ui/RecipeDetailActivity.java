package com.example.user.uand_4_baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener{

    private boolean mTwoPane;

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

                setTitle(recipe.getName());

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
            mCurrentStepFragment.updatePosition(position);
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            String CLICKED_STEP_LIST = "STEP_LIST";
            intent.putParcelableArrayListExtra(CLICKED_STEP_LIST, stepList);
            String CLICKED_STEP_POSITION = "STEP_POSITION";
            intent.putExtra(CLICKED_STEP_POSITION, position);
            startActivity(intent);
        }

    }
}
