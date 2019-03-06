package com.jxw.graphql.service;

import com.apollographql.apollo.ApolloClient;
import com.jxw.graphql.DemoApplication;
import com.jxw.graphql.type.CustomType;
import com.jxw.graphql.utils.CustomDateAdapter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class AppApolloClient {


    /**
     * private constructor.
     * Utility class
     */
    private AppApolloClient() {

    }

    /**
     * Apollo implementation method.
     * @return expected myApolloClient object.
     */

    public static ApolloClient getAppApolloClient(DemoApplication application) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return ApolloClient.builder()
                .serverUrl(application.getBaseUrl())
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.DATETIME, new CustomDateAdapter())
                .build();
    }
}

