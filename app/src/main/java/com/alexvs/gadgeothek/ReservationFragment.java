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
import android.widget.Button;

import com.alexvs.gadgeothek.domain.Reservation;
import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;

import java.util.List;


public class ReservationFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reservation_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.reservationRecyclerView);
        final ReservationAdapter reservationAdapter = new ReservationAdapter();
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
            @Override
            public void onCompletion(List<Reservation> input) {
                for (Reservation reservation:input){

                    reservationAdapter.insert(reservation);
                }
                reservationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
        recyclerView.setAdapter(reservationAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.floatingActionButton:
                Intent intent = new Intent(getContext(), AddGadgetActivity.class);
                startActivity(intent);


        }

    }
}
