package com.alexvs.gadgeothek;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvs.gadgeothek.domain.Gadget;
import com.alexvs.gadgeothek.domain.Loan;
import com.alexvs.gadgeothek.service.LibraryService;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Alexander on 22.10.2017.
 */

public class LoanAdapter extends RecyclerView.Adapter {

    ArrayList<Loan> list = new ArrayList<Loan>();


    public LoanAdapter() {
        list.add(0, new Loan("1", new Gadget("Buch"), new Date()));
        list.add(1, new Loan("2", new Gadget("DVD"), new Date()));
        list.add(2, new Loan("3", new Gadget("GoPro"), new Date()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_loan, parent, false);
        return new LoanViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LoanViewHolder) holder).bindView(position);
    }



    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

   private class LoanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       private TextView mTextViewId;
       private TextView mTextViewGadget;
       private TextView mTextViewPickUpDate;
       private TextView mTextViewReturnDate;

       public LoanViewHolder(View itemView) {
           super(itemView);
           mTextViewId = (TextView) itemView.findViewById(R.id.loan_id);
           mTextViewGadget = (TextView) itemView.findViewById(R.id.loan_gadget);
           mTextViewPickUpDate = (TextView) itemView.findViewById(R.id.loan_pickupDate);
           mTextViewReturnDate = (TextView) itemView.findViewById(R.id.loan_returnDate);
           itemView.setOnClickListener(this);
       }

       public void bindView(int position){
           mTextViewId.setText(list.get(position).getLoanId());
           mTextViewGadget.setText(list.get(position).getGadget().getName());
           mTextViewPickUpDate.setText(list.get(position).PickupDate());
           mTextViewReturnDate.setText(list.get(position).ReturnDate());

       }

       @Override
       public void onClick(View v) {

       }
   }


    // Insert a new item to the RecyclerView on a predefined position
    public void insert(Loan loan) {
        list.add(loan);

    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Loan loan) {
        int position = list.indexOf(loan);
        list.remove(position);
        notifyItemRemoved(position);
    }

}
