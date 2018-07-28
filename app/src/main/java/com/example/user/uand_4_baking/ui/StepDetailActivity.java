package com.example.user.uand_4_baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Step;

import java.util.ArrayList;
import java.util.List;

public class StepDetailActivity extends AppCompatActivity {

    private static String CLICKED_STEP_LIST = "STEP_LIST";

    private static String CLICKED_STEP_POSITION = "STEP_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intent = getIntent();

        if (intent.hasExtra(CLICKED_STEP_LIST)) {

            ArrayList<Step> stepList = intent.getParcelableArrayListExtra(CLICKED_STEP_LIST);
Log.d("STEP_DETAIL_ACTIVITY",stepList.toString());
            int position = 0;

            if(intent.hasExtra(CLICKED_STEP_POSITION)) {
                position = intent.getIntExtra(CLICKED_STEP_POSITION, 0);
Log.d("STEP_DETAIL_ACTIVITY",""+position);
            }

            StepDetailFragment currentStepFragment = new StepDetailFragment();

            currentStepFragment.setStepData(stepList, position);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, currentStepFragment)
                    .commit();
        }
    }
}
