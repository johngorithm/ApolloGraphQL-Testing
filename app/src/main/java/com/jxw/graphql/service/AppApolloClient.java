package com.jxw.graphql.service;

import com.apollographql.apollo.ApolloClient;
import com.jxw.graphql.type.CustomType;
import com.jxw.graphql.utils.CustomDateAdapter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class AppApolloClient {

    private static final String BASE_URL = "https://api.graph.cool/simple/v1/cjs1qws8c0mvr0102fkeosj4p";

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

    public static ApolloClient getAppApolloClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.DATETIME, new CustomDateAdapter())
                .build();

    }
}

