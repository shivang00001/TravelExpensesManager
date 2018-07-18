package com.trip.colleaguesexpmanager.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.adapters.CustomPagerLoginRegisterAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    //region "Binding Variables"
    private Context context;
    CustomPagerLoginRegisterAdapter customPagerSignInSignUpAdapter;
    //endregion

    //region "Overrides"
    @BindView(R.id.tab_layout) TabLayout tab_layout;
    @BindView(R.id.view_pager) ViewPager view_pager;
    //endregion

    //region "Overrides"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();
        ButterKnife.bind(this);

        setupTabLayout();
    }

    //endregion

    //region "Tab Layout Setup"
    private void setupTabLayout() {

        // TabLayout
        tab_layout.addTab(tab_layout.newTab().setText("SIGN IN"));
        tab_layout.addTab(tab_layout.newTab().setText("SIGN UP"));

        customPagerSignInSignUpAdapter = new CustomPagerLoginRegisterAdapter(this.getSupportFragmentManager(), tab_layout.getTabCount(), this);
        view_pager.setAdapter(customPagerSignInSignUpAdapter);
        view_pager.setOffscreenPageLimit(2);
        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //changeTabIcons(tab.getPosition());
                view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        reduceMarginsInTabs(tab_layout,100);

    }

    public static void reduceMarginsInTabs(TabLayout tabLayout, int marginOffset) {

        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
                }
            }

            tabLayout.requestLayout();
        }
    }
    //endregion
}
