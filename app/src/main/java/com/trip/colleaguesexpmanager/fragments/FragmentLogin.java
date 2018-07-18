package com.trip.colleaguesexpmanager.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment {


    //region "Variables"
    Context context;
    //endregion

    //region "Binding Controls"
    @BindView(R.id.buttonLogin) Button buttonLogin;
    //endregion

    //region "Overrides"
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, mainView);
        context = this.getActivity().getApplicationContext();
        onCLickButtonLogin();
        return mainView;

    }

    //endregion

    //region "Custom Events"
    private void onCLickButtonLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });
    }
    //endregion


}
