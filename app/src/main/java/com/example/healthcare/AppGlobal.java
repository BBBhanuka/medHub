package com.example.healthcare;

public class AppGlobal {
    protected static String userName = null;
    protected static double userLatitude;
    protected static double userLongitude;
    protected static String fullName = null;
    protected static String contactNumber = null;
    protected static double totalCartAmount;




    protected static void clearData() {
        userLatitude = 0;
        userLongitude = 0;
        fullName = null;
        contactNumber = null;
        totalCartAmount = 0;
    }


}
