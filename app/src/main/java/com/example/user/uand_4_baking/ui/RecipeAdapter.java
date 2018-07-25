package com.example.user.uand_4_baking.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Context mContext;

    private List<Recipe> mRecipeList;

    final private RecipeCardClickListener mOnClickListener;

    /**
     * Constructor for RecipeAdapter that accepts the context of the calling class
     *
     * @param context of the calling class
     */
    public RecipeAdapter(Context context, RecipeCardClickListener listener){
        mContext = context;
        mOnClickListener = listener;
    }

    public interface RecipeCardClickListener {
        void onRecipeClicked(Recipe clickedRecipe);
    }


    /**
     * This method creates the view holder from the inflated layout for item
     *
     * @param viewGroup
     * @param viewType
     * @return RecipeAdapterViewHolder
     */
    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item,viewGroup,false);
        return new RecipeAdapterViewHolder(view);
    }

    /**
     *
     * This method binds the items in the view holder with the data
     *
     * @param recipeAdapterViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder recipeAdapterViewHolder,int position){
        Recipe currentRecipe = mRecipeList.get(position);

       if(currentRecipe.getImage() != null && !currentRecipe.getImage().equals(""))  {
            Picasso.with(mContext)
                .load(currentRecipe.getImage())
                .placeholder(R.drawable.ic_recipe)
                .error(R.drawable.ic_error)
                .into(recipeAdapterViewHolder.mRecipeImage);
       }

        recipeAdapterViewHolder.mRecipeName.setText(currentRecipe.getName());

        recipeAdapterViewHolder.mRecipeIngredientCount.setText(Integer.toString(currentRecipe.getIngredients().size()));

        recipeAdapterViewHolder.mRecipeStepCount.setText(Integer.toString(currentRecipe.getSteps().size()));

        recipeAdapterViewHolder.mRecipeServings.setText(Integer.toString(currentRecipe.getServings()));

    }

    /**
     * @return count of total number of data items
     */
    @Override
    public int getItemCount() {
            if (null == mRecipeList) return 0;
            return mRecipeList.size();
    }

    /**
     * This inner class is used to create the ViewHolder object mapped to the layout elements
     */
    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final ImageView mRecipeImage;
        final TextView mRecipeName;
        final TextView mRecipeIngredientCount;
        final TextView mRecipeStepCount;
        final TextView mRecipeServings;

        /**
         *  Constructor to initialise the layout elements
         *
         * @param view
         */
        RecipeAdapterViewHolder(View view) {
            super(view);
            mRecipeImage = view.findViewById(R.id.iv_recipe_image);
            mRecipeName = view.findViewById(R.id.tv_recipe_name);
            mRecipeIngredientCount =  view.findViewById(R.id.tv_recipe_ingredient_count);
            mRecipeStepCount = view.findViewById(R.id.tv_recipe_step_count);
            mRecipeServings = view.findViewById(R.id.tv_recipe_servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onRecipeClicked(mRecipeList.get(getAdapterPosition()));
        }
    }

    /**
     *
     * This method can be accessed to set or modify data
     * @param recipeList
     */
    public void setRecipeData(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }


}