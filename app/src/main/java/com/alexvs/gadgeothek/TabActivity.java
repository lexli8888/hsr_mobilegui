package com.alexvs.gadgeothek;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class TabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private LoanFragment mLoanFragment;
    private ReservationFragment mReservationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Loan"));
        tabLayout.addTab(tabLayout.newTab().setText("Reservation"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(this);

        mLoanFragment = new LoanFragment();
        mReservationFragment = new ReservationFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.placeholder, mLoanFragment);
        transaction.commit();

        mLoanFragment = new LoanFragment();
        mReservationFragment = new ReservationFragment();
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        activateFragment(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void activateFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                transaction.replace(R.id.placeholder, mLoanFragment);
                transaction.commit();
                break;


            case 1:
                transaction.replace(R.id.placeholder, mReservationFragment);
                transaction.commit();
                break;


        }
    }
}
