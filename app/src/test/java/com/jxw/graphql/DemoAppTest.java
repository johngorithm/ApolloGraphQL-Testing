package com.jxw.graphql;

import com.jxw.graphql.app.DemoApplication;

import org.junit.Test;
import static org.junit.Assert.*;

public class DemoAppTest {
    @Test
    public void testGetBaseUrl() {
        DemoApplication app = new DemoApplication();

        String url = app.getBaseUrl();
        assertEquals(url, "https://api.graph.cool/simple/v1/cjs1qws8c0mvr0102fkeosj4p");
    }
}