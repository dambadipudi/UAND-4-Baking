package com.example.user.uand_4_baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    StepDetailFragment currentStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intent = getIntent();

        String RECIPE_NAME = "RECIPE_NAME";
        if(intent.hasExtra(RECIPE_NAME)) {
            setTitle(intent.getStringExtra(RECIPE_NAME));
        }

        String CLICKED_STEP_LIST = "STEP_LIST";
        if (intent.hasExtra(CLICKED_STEP_LIST)) {

            ArrayList<Step> stepList = intent.getParcelableArrayListExtra(CLICKED_STEP_LIST);
            int position = 0;

            if(savedInstanceState == null) {

                String CLICKED_STEP_POSITION = "STEP_POSITION";
                if (intent.hasExtra(CLICKED_STEP_POSITION)) {
                    position = intent.getIntExtra(CLICKED_STEP_POSITION, 0);
                }

                currentStepFragment = new StepDetailFragment();

                currentStepFragment.setStepData(stepList, position);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, currentStepFragment)
                        .commit();
            }

        }
    }

}
