package com.example.trainercompanionapp2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.Serializable;


public class GuidelineDetailFragment extends Fragment {


    EditText cdgdfguidelineTitle, cdgdfguidelineInstructions;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_GDF_TITLE = "Title";
    private static final String ARG_GDF_INSTRUCTIONS = "Instructions";


    private String arggdftitle;
    private String arggdfinstructions;

    public GuidelineDetailFragment() {

    }


    public static GuidelineDetailFragment newInstance(String gdftitle, String gdfinstructions) {
        GuidelineDetailFragment fragment = new GuidelineDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GDF_TITLE, (Serializable) gdftitle);
        args.putSerializable(ARG_GDF_INSTRUCTIONS,  (Serializable) gdfinstructions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arggdftitle = getArguments().getString(ARG_GDF_TITLE);
            arggdfinstructions = getArguments().getString(ARG_GDF_INSTRUCTIONS);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guideline_detail, container, false);

        cdgdfguidelineTitle = view.findViewById(R.id.gdfGuidelineTitle);
        cdgdfguidelineInstructions = view.findViewById(R.id.gdfGuidelineInstr);

        cdgdfguidelineTitle.setText(arggdftitle);
        cdgdfguidelineInstructions.setText(arggdfinstructions);
        return view;
    }
}