package com.trip.colleaguesexpmanager.datasources.models.trip_details;

public class Trip_user
{
    private String id;

    private String name;

    private String total_amount_spent_by_user;

    private String amount_left_or_take_trip;

    private String is_give_take;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getTotal_amount_spent_by_user() {
        return total_amount_spent_by_user;
    }

    public void setTotal_amount_spent_by_user(String total_amount_spent_by_user) {
        this.total_amount_spent_by_user = total_amount_spent_by_user;
    }

    public String getAmount_left_or_take_trip() {
        return amount_left_or_take_trip;
    }

    public void setAmount_left_or_take_trip(String amount_left_or_take_trip) {
        this.amount_left_or_take_trip = amount_left_or_take_trip;
    }

    public String getIs_give_take() {
        return is_give_take;
    }

    public void setIs_give_take(String is_give_take) {
        this.is_give_take = is_give_take;
    }
}

