package com.trip.colleaguesexpmanager.datasources.models.trip_details;

public class TripDetails
{


    private String total_amount;

    private String id;

    private String title;

    private String is_start;

    private String description;

    private String start_date;

    private String is_end;

    private Trip_user[] trip_user;

    public Trip_user[] getTrip_user ()
    {
        return trip_user;
    }

    public void setTrip_user (Trip_user[] trip_user)
    {
        this.trip_user = trip_user;
    }

    public String getTotal_amount ()
    {
        return total_amount;
    }

    public void setTotal_amount (String total_amount)
    {
        this.total_amount = total_amount;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getIs_start ()
    {
        return is_start;
    }

    public void setIs_start (String is_start)
    {
        this.is_start = is_start;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getStart_date ()
    {
        return start_date;
    }

    public void setStart_date (String start_date)
    {
        this.start_date = start_date;
    }

    public String getIs_end ()
    {
        return is_end;
    }

    public void setIs_end (String is_end)
    {
        this.is_end = is_end;
    }

}

