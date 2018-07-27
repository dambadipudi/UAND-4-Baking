package com.example.user.uand_4_baking.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.databinding.ActivityMainBinding;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.network.GetRecipeDataService;
import com.example.user.uand_4_baking.network.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
    RecipeAdapter.RecipeCardClickListener
    {

    private static String TAG = MainActivity.class.getSimpleName();

    private static String CLICKED_RECIPE_OBJECT = "RECIPE_DETAILS";

    private RelativeLayout mErrorLayout;

    private RecyclerView mRecyclerView;

    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding recipeBinding;

        recipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mErrorLayout = recipeBinding.networkErrorLayout.rlErrorLayout;

        mRecyclerView = recipeBinding.rvRecipes;

        recipeBinding.networkErrorLayout.tvRefresh.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            loadRecipes();
                                                                        }
                                                                    });

        initializeRecyclerView();

        loadRecipes();
    }

    private void initializeRecyclerView() {

        int gridSpanCount = getResources().getInteger(R.integer.grid_span_count);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, gridSpanCount);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this, this);
        mRecyclerView.setAdapter(mRecipeAdapter);
    }

    /**
     * Called when a recipe card is clicked. It creates an explicit intent to the DetailActivity class
     *
     * @param clickedRecipeObject this object is passed from the ViewHolder It needs to be a Parcelable class
     */
    @Override
    public void onRecipeClicked(Recipe clickedRecipeObject) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(CLICKED_RECIPE_OBJECT, clickedRecipeObject);
        startActivity(intent);
    }

    private void loadRecipes() {

        //Followed retrofit code from
        //https://medium.com/cr8resume/make-your-hand-dirty-with-retrofit-2-a-type-safe-http-client-for-android-and-java-c546f88b3a51

        if(NetworkUtils.isOnline(this)) {

            showRecipes();

            // Get the retrofit instance and create an object of the API class
            GetRecipeDataService service = NetworkUtils.getRetrofitInstance().create(GetRecipeDataService.class);

            // Call the method with parameter in the interface to get the recipe data
            Call<List<Recipe>> call = service.getRecipeList();

            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    List<Recipe> recipeList = response.body();
                    mRecipeAdapter.setRecipeData(recipeList);
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    Log.e(TAG, t.getMessage());
                    showErrorMessage();
                }
            });
        } else {
            showNetworkErrorMessage();
        }
    }

    private void showRecipes() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.INVISIBLE);
    }

    private void showNetworkErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
    }

}
