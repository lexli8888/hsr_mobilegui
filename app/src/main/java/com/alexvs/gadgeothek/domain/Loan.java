package com.alexvs.gadgeothek.domain;

/**
 * Created by Alexander on 21.10.2017.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Loan {

    private String id;
    private Gadget gadget;
    private Date pickupDate, returnDate;
    private final static int DAYS_TO_RETURN = 7;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");

    public Loan(String id, Gadget gadget, Date pickupDate) {
        this.id = id;
        this.gadget = gadget;
        this.pickupDate = pickupDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime( pickupDate );
        cal.add( Calendar.DATE, DAYS_TO_RETURN );
        this.returnDate = cal.getTime();
    }

    public boolean isLent() {
        return returnDate == null;
    }

    public Date getPickupDate() {
        return pickupDate;

    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String ReturnDate(){
        return simpleDateFormat.format(returnDate);
    }

    public String PickupDate(){
        return simpleDateFormat.format(pickupDate);
    }

    public String getLoanId() {
        return id;
    }

    public boolean isOverdue() {
        if (!isLent())
            return false;
        return overDueDate().after(new Date());
    }

    public Date overDueDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pickupDate);
        cal.add(Calendar.DATE, DAYS_TO_RETURN); // add DAYS_TO_RETURN days
        return cal.getTime();
    }

    public Gadget getGadget() {
        return gadget;
    }

    public void setGadget(Gadget gadget) {
        this.gadget = gadget;
    }
}