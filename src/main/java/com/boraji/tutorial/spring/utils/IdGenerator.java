package com.boraji.tutorial.spring.utils;

public class IdGenerator {

    public static long userID = 1L;
    public static long itemId = 1L;
    public static long orderID = 1L;

    public static long getUserID() {
        return userID++;
    }

    public static long getItemId() {
        return itemId++;
    }

    public static long getOrderID() {
        return orderID++;
    }
}
