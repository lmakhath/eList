package com.elist.knormal.commons;

public abstract class ShopriteCommons {

    public static final String NUMBER_OF_ITEMS = "total-number-of-results pull-right";
    public static final String FOOD_BASE_URL = "https://www.shoprite.co.za/c-2413/All-Departments/Food";
    public static final String FOOD_URL = FOOD_BASE_URL + "?q=%3Arelevance%3AbrowseAllStoresFacetOff%3AbrowseAllStoresFacetOff&page=";
    public static final String DRINKS_BASE_URL = "https://www.shoprite.co.za/c-2257/All-Departments/Drinks";
    public static final String DRINK_URL = DRINKS_BASE_URL + "?q=%3Arelevance%3AbrowseAllStoresFacetOff%3AbrowseAllStoresFacetOff&page=";
    public static final String HOUSEHOLD_BASE_URL = "https://www.shoprite.co.za/c-2721/All-Departments/Household";
    public static final String HOUSEHOLD_URL = HOUSEHOLD_BASE_URL + "?q=%3Arelevance%3AbrowseAllStoresFacetOff%3AbrowseAllStoresFacetOff&page=";

    public static final String[] LINKS = {FOOD_URL, DRINK_URL, HOUSEHOLD_URL};
    public static final String[] BASE_URLS = {FOOD_BASE_URL, DRINKS_BASE_URL, HOUSEHOLD_BASE_URL};
}
