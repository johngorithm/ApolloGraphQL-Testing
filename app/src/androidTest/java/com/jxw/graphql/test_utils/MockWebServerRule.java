package com.jxw.graphql.test_utils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import okhttp3.mockwebserver.MockWebServer;

public class MockWebServerRule implements TestRule {
    public final MockWebServer server = new MockWebServer();
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                server.start(9900);
                base.evaluate();
                server.shutdown();
            }
        };
    }
}
