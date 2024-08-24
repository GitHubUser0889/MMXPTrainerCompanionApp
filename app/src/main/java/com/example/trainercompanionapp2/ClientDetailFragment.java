package com.example.trainercompanionapp2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;


public class ClientDetailFragment extends Fragment {

    EditText cdfview_clientname, cdfview_clientage, cdfview_clientweight, cdfview_clientheight,
             cdfview_clientgender, cdfview_clientcontact, cdfview_clientworkout, cdfview_clientguideline,
             cdfview_clientnotes;

    ProgressBar cdfview_SessionCounter;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10 = "param10";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private String mParam7;
    private String mParam8;
    private String mParam9;
    private String mParam10;

    public ClientDetailFragment() {
        // Required empty public constructor
    }

    public static ClientDetailFragment newInstance(String param1, String param2, String param3, String param4, String param5,
    String param6, String param7, String param8, String param9, String param10) {
        ClientDetailFragment fragment = new ClientDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        args.putString(ARG_PARAM9, param9);
        args.putString(ARG_PARAM10, param10);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getString(ARG_PARAM7);
            mParam8 = getArguments().getString(ARG_PARAM8);
            mParam9 = getArguments().getString(ARG_PARAM9);
            mParam10 = getArguments().getString(ARG_PARAM10);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_client_detail, container, false);

                        cdfview_clientname = view.findViewById(R.id.view_clientname);
                        cdfview_clientage = view.findViewById(R.id.view_clientage);
                        cdfview_clientcontact = view.findViewById(R.id.view_clientcontact);
                        cdfview_clientgender = view.findViewById(R.id.view_clientgender);
                        cdfview_clientweight = view.findViewById(R.id.view_clientweight);
                        cdfview_clientheight = view.findViewById(R.id.view_clientheight);
                        cdfview_clientworkout = view.findViewById(R.id.view_clientworkout);
                        cdfview_clientguideline = view.findViewById(R.id.view_clientguideline);
                        cdfview_SessionCounter = view.findViewById(R.id.view_SessionCounter);
                        cdfview_clientnotes = view.findViewById(R.id.view_clientnotes);

        cdfview_clientname.setText(mParam1);
        cdfview_clientage.setText(mParam2);
        cdfview_clientcontact.setText(mParam3);
        cdfview_clientgender.setText(mParam4);
        cdfview_clientweight.setText(mParam5);
        cdfview_clientheight.setText(mParam6);
        cdfview_clientworkout.setText(mParam7);
        cdfview_clientguideline.setText(mParam8);
        cdfview_clientnotes.setText(mParam10);

        int progresscounter = Integer.parseInt(mParam9);
        cdfview_SessionCounter.setProgress(progresscounter + 10);
        cdfview_SessionCounter.setMax(100);


        return view;
    }
}