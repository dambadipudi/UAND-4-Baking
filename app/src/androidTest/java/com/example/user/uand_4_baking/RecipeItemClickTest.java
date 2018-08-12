package com.example.user.uand_4_baking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.user.uand_4_baking.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class RecipeItemClickTest {

    public static final String RECIPE_NAME = "Brownies";

    public static final int RECIPE_POSITION = 1;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem_OpensRecipeDetailActivity() {

        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(RECIPE_POSITION, click()));

        //Code for recycler view test
        // adapted from https://github.com/ravidsrk/espresso-utils/blob/master/EspressoUtils.java

        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(RECIPE_NAME))));

    }

    private static Matcher<Object> withToolbarTitle(final Matcher<String> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
            @Override public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

}
