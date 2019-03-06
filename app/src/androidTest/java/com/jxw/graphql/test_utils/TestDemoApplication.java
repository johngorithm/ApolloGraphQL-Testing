package com.jxw.graphql.test_utils;

import com.jxw.graphql.DemoApplication;

public class TestDemoApplication extends DemoApplication {
    private String baseUrl;

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }
}
