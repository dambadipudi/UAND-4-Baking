package com.example.user.uand_4_baking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Step;

import java.util.List;

public class StepDetailFragment extends Fragment {

    private TextView videoURLTextView;

    private TextView descriptionTextView;

    private List<Step> mStepList;

    private Button nextButton;

    private Button previousButton;

    private int mListIndex;

    // Mandatory empty constructor
    public StepDetailFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        videoURLTextView = rootView.findViewById(R.id.video_player);

        descriptionTextView = rootView.findViewById(R.id.tv_step_description);

        updateStepData();

        nextButton = rootView.findViewById(R.id.next_button);
        if (mStepList!= null && mListIndex == mStepList.size() - 1) {
            nextButton.setEnabled(false);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStepList!= null && mListIndex < mStepList.size() - 1) {
                    updatePosition(mListIndex + 1);
                }
            }
        });

        previousButton = rootView.findViewById(R.id.previous_button);
        if(mListIndex == 0) {
            previousButton.setEnabled(false);
        }
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListIndex > 0) {
                   updatePosition(mListIndex - 1);
                }
            }
        });

        return rootView;
    }

    public void setStepData(List<Step> stepList, int position) {
        mStepList = stepList;
        mListIndex = position;
    }

    public void updatePosition(int position) {
        mListIndex = position;
        previousButton.setEnabled(true);
        nextButton.setEnabled(true);
        if(mListIndex == 0) {
            previousButton.setEnabled(false);
        } else if (mListIndex == mStepList.size() - 1) {
            nextButton.setEnabled(false);
        }
        updateStepData();
    }

    private void updateStepData() {
        if(videoURLTextView != null && mStepList != null && mStepList.get(mListIndex) != null) {
            videoURLTextView.setText(mStepList.get(mListIndex).getVideoURL());
        }
        if(descriptionTextView != null && mStepList != null && mStepList.get(mListIndex) != null) {
            descriptionTextView.setText(mStepList.get(mListIndex).getDescription());
        }
        if(getActivity() != null && mStepList != null && mStepList.get(mListIndex) != null) {
            getActivity().setTitle(mStepList.get(mListIndex).getShortDescription());
        }
    }

    public int getPosition() {
        return mListIndex;
    }

}
