package com.alexvs.gadgeothek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.alexvs.gadgeothek.domain.Gadget;
import com.alexvs.gadgeothek.domain.Loan;
import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;

import java.util.List;


public class LoanFragment extends Fragment implements View.OnClickListener {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_list, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.loanRecyclerView);
        final LoanAdapter loanAdapter = new LoanAdapter();

        LibraryService.getLoansForCustomer(new Callback<List<Loan>>() {
            @Override
            public void onCompletion(List<Loan> input) {
                for (Loan loan:input) {
                    loanAdapter.insert(loan);
                }
                loanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });

        recyclerView.setAdapter(loanAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;

    }


    @Override
    public void onClick(View v) {
        switch (v.toString()){

            case "floatingActionButton" :
                Intent intent = new Intent(this.getContext(), AddGadgetActivity.class);
                startActivity(intent);
                break;
            default: return;

        }
    }
}
