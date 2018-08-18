package com.example.user.uand_4_baking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.user.uand_4_baking.model.Ingredient;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.ui.RecipeDetailActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    private static final String CLICKED_RECIPE_OBJECT = "RECIPE_DETAILS";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId, Recipe selectedRecipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        if(selectedRecipe != null) {
            views.setTextViewText(R.id.appwidget_recipe_name, selectedRecipe.getName());
            views.setTextViewText(R.id.appwidget_ingredient_list, getIngredientString(selectedRecipe.getIngredients()));

            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra(CLICKED_RECIPE_OBJECT, selectedRecipe);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.appwidget_ingredient_list, pendingIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static String getIngredientString(List<Ingredient> ingredientList) {
        StringBuilder ingredientString = new StringBuilder();
        for(Ingredient currentIngredient : ingredientList) {
            ingredientString.append(currentIngredient.getQuantity() + " "
                    + currentIngredient.getMeasure() + " "
                    + currentIngredient.getIngredient() + "\n");
        }
        return ingredientString.toString();
    }

    public static void updateSelectedRecipe(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }
}

