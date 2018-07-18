package com.trip.colleaguesexpmanager;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActionList extends Fragment {


    private Context context;

    public LinearLayout outerLinearLayout;
    public LinearLayout linearLayoutHeading;
    public TextView textViewHeadingText;

    public ImageView imageView1;
    public ImageView imageView2;
    public ImageView imageView3;

    public LinearLayout linearLayoutItem1;
    public LinearLayout linearLayoutItem2;
    public LinearLayout linearLayoutItem3;

    public LinearLayout linearLayoutSeprator1;
    public LinearLayout linearLayoutSeprator2;

    public TextView textViewFieldItem1;
    public TextView textViewFieldItem2;
    public TextView textViewFieldItem3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View mainView = inflater.inflate(R.layout.fragment_action_list, container, false);
        ButterKnife.bind(this, mainView);
        context = this.getActivity().getApplicationContext();
        outerLinearLayout = mainView.findViewById(R.id.outerLinearLayout);
        linearLayoutHeading = mainView.findViewById(R.id.linearLayoutHeading);
        textViewHeadingText = mainView.findViewById(R.id.textViewHeadingText);

        linearLayoutItem1 = mainView.findViewById(R.id.linearLayoutItem1);
        linearLayoutItem2 = mainView.findViewById(R.id.linearLayoutItem2);
        linearLayoutItem3 = mainView.findViewById(R.id.linearLayoutItem3);

        linearLayoutSeprator1 = mainView.findViewById(R.id.linearLayoutSeprator1);
        linearLayoutSeprator2 = mainView.findViewById(R.id.linearLayoutSeprator2);

        textViewFieldItem1 = mainView.findViewById(R.id.textViewFieldItem1);
        textViewFieldItem2 = mainView.findViewById(R.id.textViewFieldItem2);
        textViewFieldItem3 = mainView.findViewById(R.id.textViewFieldItem3);

        imageView1 = mainView.findViewById(R.id.imageView1);
        imageView2 = mainView.findViewById(R.id.imageView2);
        imageView3 = mainView.findViewById(R.id.imageView3);


        return mainView;
    }

}
