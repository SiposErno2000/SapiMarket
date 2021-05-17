package com.example.sapimarket.ui.constants;

public class Constants {
    public final static String USERS = "users";
    public final static String ITEMS = "items";
    public static String CURRENT_CHILD;

    public static String getCurrentChild() {
        return CURRENT_CHILD;
    }

    public static void setCurrentChild(String currentChild) {
        CURRENT_CHILD = currentChild;
    }
}
