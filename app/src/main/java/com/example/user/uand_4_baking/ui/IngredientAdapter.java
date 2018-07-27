package com.example.user.uand_4_baking.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private Context mContext;

    private List<Ingredient> mIngredientList;

    /**
     * Constructor for IngredientAdapter that accepts the context of the calling class
     *
     * @param context of the calling class
     */
    public IngredientAdapter(Context context){
        mContext = context;
    }

    /**
     * This method creates the view holder from the inflated layout for item
     *
     * @param viewGroup
     * @param viewType
     * @return IngredientAdapterViewHolder
     */
    @NonNull
    @Override
    public IngredientAdapter.IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_item,viewGroup,false);
        return new IngredientAdapter.IngredientAdapterViewHolder(view);
    }

    /**
     *
     * This method binds the items in the view holder with the data
     *
     * @param ingredientAdapterViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientAdapterViewHolder ingredientAdapterViewHolder, int position){
        Ingredient currentIngredient = mIngredientList.get(position);

        ingredientAdapterViewHolder.mIngredientQuantity.setText(Float.toString(currentIngredient.getQuantity()));

        ingredientAdapterViewHolder.mIngredientMeasure.setText(currentIngredient.getMeasure());

        ingredientAdapterViewHolder.mIngredientName.setText(currentIngredient.getIngredient());

    }

    /**
     * @return count of total number of data items
     */
    @Override
    public int getItemCount() {
        if (null == mIngredientList) return 0;
        return mIngredientList.size();
    }

    /**
     * This inner class is used to create the ViewHolder object mapped to the layout elements
     */
    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView mIngredientQuantity;
        final TextView mIngredientMeasure;
        final TextView mIngredientName;

        /**
         *  Constructor to initialise the layout elements
         *
         * @param view
         */
        IngredientAdapterViewHolder(View view) {
            super(view);
            mIngredientQuantity = view.findViewById(R.id.tv_ingredient_quantity);
            mIngredientMeasure = view.findViewById(R.id.tv_ingredient_measure);
            mIngredientName =  view.findViewById(R.id.tv_ingredient_name);
        }

    }

    /**
     *
     * This method can be accessed to set or modify data
     * @param ingredientList
     */
    public void setIngredientData(List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
        notifyDataSetChanged();
    }
}