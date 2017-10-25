package com.alexvs.gadgeothek;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.alexvs.gadgeothek.Model.Gadget;
import com.alexvs.gadgeothek.Model.Loan;

import java.util.Date;
import java.util.List;


public class LoanFragment extends Fragment {

    Loan first = new Loan("1", new Gadget("Buch"), new Date(1508803200L), new Date(1508903200L));
    Loan second = new Loan("2", new Gadget("DVD"), new Date(1508803200L), new Date(1508903200L));
    Loan third = new Loan("3", new Gadget("CD"), new Date(1508803200L), new Date(1508903200L));



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.loanRecyclerView);
        Loan_Adapter loanAdapter = new Loan_Adapter();
        loanAdapter.insert(third);
        loanAdapter.insert(second);
        loanAdapter.insert(first);
        recyclerView.setAdapter(loanAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;

    }



}
