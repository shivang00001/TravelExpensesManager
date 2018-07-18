package com.trip.colleaguesexpmanager.classes;

public class NavItem {
    public String mTitle;
    public int mIcon;


    public NavItem(String title, String subtitle, String showDivider, int icon) {
        setmTitle(title);
        setmIcon(icon);
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }


}

