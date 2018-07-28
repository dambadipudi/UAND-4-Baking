package com.example.user.uand_4_baking.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Recipe;
import com.example.user.uand_4_baking.model.Step;

import java.util.ArrayList;

//Static fragment to display the Recipe ingredients and List of Steps
public class RecipeDetailFragment extends Fragment implements StepAdapter.StepClickListener{

    private RecyclerView mIngredientRecyclerView;

    private RecyclerView mStepRecyclerView;

    private IngredientAdapter mIngredientAdapter;

    private StepAdapter mStepAdapter;

    private OnStepClickListener mCallback;

    private Recipe mCurrentRecipe;

    // Mandatory empty constructor
    public RecipeDetailFragment() {
    }

    @Override
    public void onStepClicked(int position) {
        mCallback.onStepClicked((ArrayList<Step>) mCurrentRecipe.getSteps(), position);
    }

    public interface OnStepClickListener {
        void onStepClicked(ArrayList<Step> stepList, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        mIngredientRecyclerView = rootView.findViewById(R.id.rv_ingredients);
        initializeIngredientRecyclerView();

        mStepRecyclerView = rootView.findViewById(R.id.rv_steps);
        initializeStepRecyclerView();

        // Return the root view
        return rootView;
    }

    private void initializeIngredientRecyclerView() {

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity());
        mIngredientRecyclerView.setLayoutManager(layoutManager);

        mIngredientRecyclerView.setHasFixedSize(true);

        mIngredientAdapter = new IngredientAdapter(getActivity());
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);
    }

    private void initializeStepRecyclerView() {

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity());
        mStepRecyclerView.setLayoutManager(layoutManager);

        mStepRecyclerView.setHasFixedSize(true);

        mStepAdapter = new StepAdapter(getActivity(), this);
        mStepRecyclerView.setAdapter(mStepAdapter);
    }

    public void setRecipeData(Recipe recipe) {
        mCurrentRecipe = recipe;
        mIngredientAdapter.setIngredientData(recipe.getIngredients());
        mStepAdapter.setStepData(recipe.getSteps());
    }

}
