package com.alexvs.gadgeothek;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alexvs.gadgeothek.domain.Loan;
import com.alexvs.gadgeothek.domain.Reservation;
import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Alexander on 25.10.2017.
 */

public class ReservationAdapter extends RecyclerView.Adapter {

    ArrayList<Reservation> list = new ArrayList<Reservation>();

    public ReservationAdapter(){

    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_reservation, parent, false);
        return new ReservationViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ReservationViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextViewId;
        private TextView mTextViewReservation;
        private Button mButtonDelete;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            mTextViewId = (TextView) itemView.findViewById(R.id.reservation_id);
            mTextViewReservation = (TextView) itemView.findViewById(R.id.reservation_content);
            mButtonDelete = (Button) itemView.findViewById(R.id.reservation_delete);
            mButtonDelete.setOnClickListener(this);
        }

        public void bindView(int position){
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            mTextViewId.setText(dt1.format(list.get(position).getReservationDate()));
            mTextViewReservation.setText(list.get(position).getGadget().getName());
            mButtonDelete.setId(position);

        }

        @Override
        public void onClick(View v) {
            Reservation reservation = list.get(v.getId());
            LibraryService.deleteReservation(reservation, new Callback<Boolean>() {
                @Override
                public void onCompletion(Boolean input) {
                    System.out.println("Reservation deleted");
                }

                @Override
                public void onError(String message) {
                    System.out.println(message);
                }
            });
            remove(reservation);


        }
    }


    // Insert a new item to the RecyclerView on a predefined position
    public void insert(Reservation reservation) {

            list.add(reservation);

    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Reservation reservation) {
        int position = list.indexOf(reservation);
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
