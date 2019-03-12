package com.jxw.graphql;

import com.apollographql.apollo.response.CustomTypeValue;
import com.jxw.graphql.utils.CustomDateAdapter;

import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.*;

public class ScalarAdapterTest {
    @Test
    public void testDecodeDateSuccessfully() {
        /**
         * CustomDateAdapter class to be unit tested.
         */
        CustomDateAdapter dateAdapter = new CustomDateAdapter();
        // Creating a custom graphQL string
        CustomTypeValue graphQLString = CustomTypeValue.fromRawValue("2019-02-12T14:07:53.000Z");
        // decoding the custom graphQL string to Java Date string
        String javaDataString = dateAdapter.decode(graphQLString).toString();
        // testing
        assertEquals("Tue Feb 12 14:07:53 WAT 2019", javaDataString);
    }
    @Test(expected = RuntimeException.class)
    public void testDecodeDateWithError() {
        /**
         * CustomDateAdapter class to be unit tested.
         */
        CustomDateAdapter dateAdapter = new CustomDateAdapter();
        // Creating a custom graphQL string
        CustomTypeValue graphQLString = CustomTypeValue.fromRawValue("2019-02-");

        // should throw an error
        // decoding the custom graphQL string to Java Date string
        dateAdapter.decode(graphQLString);

    }
    @Test
    public void testEncodeDataSuccessfully() {
        /**
         * CustomDateAdapter class to be unit tested.
         */
        CustomDateAdapter dateAdapter = new CustomDateAdapter();
        Date date = new Date(2019 - 1900, 1, 12);

        CustomTypeValue graphString = dateAdapter.encode(date);

        assertEquals("2019-02-12T00:00:00", graphString.value.toString());
    }
}
