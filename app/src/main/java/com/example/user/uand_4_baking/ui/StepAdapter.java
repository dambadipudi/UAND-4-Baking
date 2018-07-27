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
import com.example.user.uand_4_baking.model.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private Context mContext;

    private List<Step> mStepList;

    final private StepAdapter.StepClickListener mOnClickListener;

    /**
     * Constructor for RecipeAdapter that accepts the context of the calling class
     *
     * @param context of the calling class
     */
    public StepAdapter(Context context, StepAdapter.StepClickListener listener){
        mContext = context;
        mOnClickListener = listener;
    }

    public interface StepClickListener {
        void onStepClicked(Step clickedStep);
    }

    /**
     * This method creates the view holder from the inflated layout for item
     *
     * @param viewGroup
     * @param viewType
     * @return StepAdapterViewHolder
     */
    @NonNull
    @Override
    public StepAdapter.StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_item,viewGroup,false);
        return new StepAdapter.StepAdapterViewHolder(view);
    }

    /**
     *
     * This method binds the items in the view holder with the data
     *
     * @param stepAdapterViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepAdapterViewHolder stepAdapterViewHolder, int position){
        Step currentStep = mStepList.get(position);

        if(currentStep.getThumbnailURL() != null && !currentStep.getThumbnailURL().equals(""))  {
            Picasso.with(mContext)
                    .load(currentStep.getThumbnailURL())
                    .placeholder(R.drawable.ic_recipe)
                    .error(R.drawable.ic_error)
                    .into(stepAdapterViewHolder.mStepImage);
        }

        stepAdapterViewHolder.mStepTitle.setText(currentStep.getShortDescription());
    }

    /**
     * @return count of total number of data items
     */
    @Override
    public int getItemCount() {
        if (null == mStepList) return 0;
        return mStepList.size();
    }

    /**
     * This inner class is used to create the ViewHolder object mapped to the layout elements
     */
    public class StepAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final ImageView mStepImage;
        final TextView mStepTitle;

        /**
         *  Constructor to initialise the layout elements
         *
         * @param view
         */
        StepAdapterViewHolder(View view) {
            super(view);
            mStepImage = view.findViewById(R.id.iv_step_image);
            mStepTitle = view.findViewById(R.id.tv_step_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onStepClicked(mStepList.get(getAdapterPosition()));
        }
    }

    /**
     *
     * This method can be accessed to set or modify data
     * @param stepList
     */
    public void setStepData(List<Step> stepList) {
        mStepList = stepList;
        notifyDataSetChanged();
    }


}
