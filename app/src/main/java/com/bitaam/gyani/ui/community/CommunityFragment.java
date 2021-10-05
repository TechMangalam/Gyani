package com.bitaam.gyani.ui.community;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitaam.gyani.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CommunityFragment extends Fragment {

    FloatingActionButton askQuestionBtn;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        askQuestionBtn = view.findViewById(R.id.askQuestionFloatingActionBtn);


        onClickAndTouchEvents();


        return view;
    }

    private void onClickAndTouchEvents() {

        askQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),DoubtQuestionPostingActivity.class));
            }
        });

    }


}