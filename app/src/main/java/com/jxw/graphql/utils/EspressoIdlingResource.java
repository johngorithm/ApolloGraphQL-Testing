package com.jxw.graphql.utils;

import android.support.test.espresso.idling.CountingIdlingResource;

public class EspressoIdlingResource {

    private static final String RESOURCE_ID = "Wait";
    private static CountingIdlingResource countingIdlingResource = new CountingIdlingResource(RESOURCE_ID);

    private EspressoIdlingResource() {} //NOPMD

    public static void increment() {
        countingIdlingResource.increment();
    }

    public static void decrement() {
        countingIdlingResource.decrement();
    }

    public static CountingIdlingResource getCountIdlingResource() {
        return countingIdlingResource;
    }
}