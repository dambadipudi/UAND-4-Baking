package com.example.user.uand_4_baking;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.model.Step;
import com.example.user.uand_4_baking.ui.RecipeDetailActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class StepItemClickTest {

    public static final String STEP_TITLE = "Recipe Introduction";

    public static final int STEP_POSITION = 0;

    //Code to pass intent to the activity adapted from https://stackoverflow.com/questions/31752303/espresso-startactivity-that-depends-on-intent

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            Recipe recipeObject = new Recipe();
            List<Step> stepList = new ArrayList<>();
            Step step = new Step();
            step.setShortDescription(STEP_TITLE);
            stepList.add(step);
            recipeObject.setSteps(stepList);

            String CLICKED_RECIPE_OBJECT = "RECIPE_DETAILS";
            intent.putExtra(CLICKED_RECIPE_OBJECT, recipeObject);
            return intent;
        }
    };

    @Test
    public void clickStepItem_OpensStepDetailActivity() {

        onView(withId(R.id.rv_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(STEP_POSITION, click()));

        onView(withId(R.id.tv_step_title))
                .check(matches(withText(STEP_TITLE)));

    }
}
