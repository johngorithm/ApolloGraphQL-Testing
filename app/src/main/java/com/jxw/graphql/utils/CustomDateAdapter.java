package com.jxw.graphql.utils;

import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateAdapter implements CustomTypeAdapter<Date> {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Date decode(@NotNull CustomTypeValue value) {
        try {
            return DATE_FORMAT.parse(value.value.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public CustomTypeValue encode(@NotNull Date value) {
        return new CustomTypeValue.GraphQLString(DATE_FORMAT.format(value));
    }
}
