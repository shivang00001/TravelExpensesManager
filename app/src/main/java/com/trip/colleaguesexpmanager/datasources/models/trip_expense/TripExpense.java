package com.trip.colleaguesexpmanager.datasources.models.trip_expense;

/**
 * Created by my on 21/Apr/2018.
 */

public class TripExpense {
    private String id;
    private String title;
    private String amount;
    private String expense_by_name;
    private String expense_by_id;
    private String expense_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpense_by_name() {
        return expense_by_name;
    }

    public void setExpense_by_name(String expense_by_name) {
        this.expense_by_name = expense_by_name;
    }

    public String getExpense_by_id() {
        return expense_by_id;
    }

    public void setExpense_by_id(String expense_by_id) {
        this.expense_by_id = expense_by_id;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(String expense_date) {
        this.expense_date = expense_date;
    }
}
