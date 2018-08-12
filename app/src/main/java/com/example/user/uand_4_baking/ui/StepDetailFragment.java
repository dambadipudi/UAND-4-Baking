package com.example.user.uand_4_baking.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.uand_4_baking.R;
import com.example.user.uand_4_baking.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class StepDetailFragment extends Fragment {

    private TextView titleTextView;

    private TextView descriptionTextView;

    private List<Step> mStepList;

    private Button nextButton;

    private Button previousButton;

    private int mListIndex;

    private PlayerView videoPlayer;

    private SimpleExoPlayer mExoPlayer;

    boolean mPlayerStatus = true;

    long mPlayerPosition = 0;

    private static final String CURRENT_PLAYER_POSITION = "CURRENT_PLAYER_POSITION";

    private static final String CURRENT_PLAYER_STATUS = "CURRENT_PLAYER_STATUS";

    // Mandatory empty constructor
    public StepDetailFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        videoPlayer = rootView.findViewById(R.id.video_player);

        setRetainInstance(true);

        initializePlayer();

        descriptionTextView = rootView.findViewById(R.id.tv_step_description);

        titleTextView = rootView.findViewById(R.id.tv_step_title);

        updateStepData();

        nextButton = rootView.findViewById(R.id.next_button);
        if (mStepList!= null && mListIndex == mStepList.size() - 1) {
            nextButton.setEnabled(false);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStepList!= null && mListIndex < mStepList.size() - 1) {
                    resetPlayer();
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
                    resetPlayer();
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
        if(mExoPlayer != null && mStepList != null && mStepList.get(mListIndex) != null) {
            if(mStepList.get(mListIndex).getVideoURL() == null || mStepList.get(mListIndex).getVideoURL().equals("")) {
                videoPlayer.setVisibility(View.GONE);
            }
            else {
                videoPlayer.setVisibility(View.VISIBLE);
                // Prepare the MediaSource.
                Uri mediaUri = Uri.parse(mStepList.get(mListIndex).getVideoURL());
                String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                        getActivity(), userAgent);
                MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(mPlayerStatus);
                mExoPlayer.seekTo(mPlayerPosition);
            }
        }

        if(descriptionTextView != null && mStepList != null && mStepList.get(mListIndex) != null) {
            descriptionTextView.setText(mStepList.get(mListIndex).getDescription());
        }

        if(titleTextView != null && mStepList != null && mStepList.get(mListIndex) != null) {
            titleTextView.setText(mStepList.get(mListIndex).getShortDescription());
        }

    }

    public int getPosition() {
        return mListIndex;
    }

    /**
     * Initialize the video player
     */
    private void initializePlayer() {
        if(mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            videoPlayer.setPlayer(mExoPlayer);
        }
    }

    public void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    public void resetPlayer() {
        mExoPlayer.stop();
        mPlayerPosition = 0;
        mPlayerStatus = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_PLAYER_POSITION, mExoPlayer.getCurrentPosition());
        outState.putBoolean(CURRENT_PLAYER_STATUS, mExoPlayer.getPlayWhenReady());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(CURRENT_PLAYER_POSITION);
            mPlayerStatus = savedInstanceState.getBoolean(CURRENT_PLAYER_STATUS);
            updateStepData();
        }
    }
}
