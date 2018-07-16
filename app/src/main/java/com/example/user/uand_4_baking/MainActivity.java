package com.example.user.uand_4_baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.network.GetRecipeDataService;
import com.example.user.uand_4_baking.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRecipes();
    }

    public void loadRecipes() {

        //Followed retrofit code from
        //https://medium.com/cr8resume/make-your-hand-dirty-with-retrofit-2-a-type-safe-http-client-for-android-and-java-c546f88b3a51

        // Create handle for the RetrofitInstance interface
        GetRecipeDataService service = RetrofitInstance.getRetrofitInstance().create(GetRecipeDataService.class);

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
                Toast.makeText(MainActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
