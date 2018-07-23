package com.example.user.uand_4_baking.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding recipeBinding;

    RelativeLayout errorRelativeLayout;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        errorRelativeLayout = recipeBinding.networkErrorLayout.rlErrorLayout;

        recyclerView = recipeBinding.rvRecipes;

        recipeBinding.networkErrorLayout.tvRefresh.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            loadRecipes();
                                                                        }
                                                                    });

        loadRecipes();
    }

    public void loadRecipes() {

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
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    List<Recipe> recipeList = response.body();
                    for (Recipe recipe : recipeList) {
                        Log.d(TAG, recipe.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    showErrorMessage();
                }
            });
        } else {
            showNetworkErrorMessage();
        }
    }

    private void showRecipes() {
        recyclerView.setVisibility(View.VISIBLE);
        errorRelativeLayout.setVisibility(View.INVISIBLE);
    }

    private void showNetworkErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        errorRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
    }

}
