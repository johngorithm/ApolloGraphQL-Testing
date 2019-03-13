package com.jxw.graphql;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.jxw.graphql.test_utils.MockWebServerRule;
import com.jxw.graphql.test_utils.TestDemoApplication;
import com.jxw.graphql.utils.EspressoIdlingResource;
import com.jxw.graphql.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.not;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private CountingIdlingResource countingIdlingResource = EspressoIdlingResource.getCountIdlingResource();

    /**
     * TODO: Move the data containing variables into a data class
     */
    private static final String MOCK_RESPONSE = "{" +
            "\"data\":{" +
            "   \"allTeachers\":[" +
            "       {" +
            "           \"name\":\"John Doe\"," +
            "           \"subject\":\"cs300\"," +
            "           \"__typename\":\"Teacher\"," +
            "           \"id\":\"cjs1ucrri6yqe016794qlaclq\"," +
            "           \"createdAt\":\"2019-02-12T14:07:53.000Z\"" +
            "       }," +
            "       {" +
            "           \"name\":\"Jane Doe\"," +
            "           \"subject\":\"SLT101\"," +
            "           \"__typename\":\"Teacher\"," +
            "           \"id\":\"cjs1uixtu1f6g019507d0ksrk\"," +
            "           \"createdAt\":\"2019-02-12T14:12:41.000Z\"" +
            "       }" +
            "   ]" +
            "}}";

    private static final String ERROR_RESPONSE = "{\n" +
            "  \"data\": null,\n" +
            "  \"errors\": [\n" +
            "    {\n" +
            "      \"message\": \"Cannot query field 'subjecta'\",\n" +
            "      \"locations\": [\n" +
            "        {\n" +
            "          \"line\": 5,\n" +
            "          \"column\": 5\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private static final String INVALID_RESPONSE = "{" +
            "\"data\":{" +
            "   \"allTeachers\":[" +
            "       {" +
            "           \"name\":\"John Doe\"," +
            "           \"subject\":\"cs300\"," +
            "           \"id\":\"cjs1ucrri6yqe016794qlaclq\"," +
            "           \"createdAt\":\"2019-02-12T14:07:53.000Z\"" +
            "       }," +
            "       {" +
            "           \"name\":\"Jane Doe\"," +
            "           \"subject\":\"SLT\"," +
            "           \"id\":\"cjs1uixtu1f6g019507d0ksrk\"," +
            "           \"createdAt\":\"2019-02-12T14:12:41.000Z\"" +
            "       }" +
            "   ]" +
            "}}";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Before
    public void setUp() {
        // Espresso Idling Resource registration
        IdlingRegistry.getInstance().register(countingIdlingResource);

        /**
         * Setting localhost as base url for testing by casting the application context
         * onto the TestDemoApplication context.
         */
        TestDemoApplication app = (TestDemoApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setBaseUrl(mockWebServerRule.server.url("/").toString());
    }

    @Test
    public void testFirstTeacherIsDisplayed() {
        // Setting local server response
        mockWebServerRule.server.enqueue(new MockResponse().setBody(MOCK_RESPONSE));
        // LAUNCH ACTIVITY
        mainActivityTestRule.launchActivity(null);
        // Actual testing
        onView(withText("John Doe")).check(matches(isDisplayed()));
    }

    @Test
    public void testSecondTeacherIsDisplayed() {
        // Setting local server response
        mockWebServerRule.server.enqueue(new MockResponse().setBody(MOCK_RESPONSE));
        // LAUNCH ACTIVITY
        mainActivityTestRule.launchActivity(null);
        // Actual testing
        onView(withText("Jane Doe")).check(matches(isDisplayed()));
    }

    @Test
    public void testQueryError() {

        // Setting local server response
        mockWebServerRule.server.enqueue(new MockResponse().setBody(ERROR_RESPONSE));
        // LAUNCH ACTIVITY
        mainActivityTestRule.launchActivity(null);
        // Actual testing
        onView(withId(R.id.tv_first_teacher_name)).check(matches(withText("Cannot query field 'subjecta'")));
        onView(withId(R.id.tv_second_teacher_name)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testInvalidResponse() {

        // Setting local server response
        mockWebServerRule.server.enqueue(new MockResponse().setBody(INVALID_RESPONSE));
        // LAUNCH ACTIVITY
        mainActivityTestRule.launchActivity(null);
        // Actual testing
        onView(withId(R.id.tv_first_teacher_name)).check(matches(withText("Failed to parse http response")));
        onView(withId(R.id.tv_second_teacher_name)).check(matches(not(isDisplayed())));

    }

    @Test
    public void testValidRequestPath() throws InterruptedException {

        // Setting local server response
        mockWebServerRule.server.enqueue(new MockResponse().setBody(MOCK_RESPONSE));
        // LAUNCH ACTIVITY
        mainActivityTestRule.launchActivity(null);


        RecordedRequest recordedRequest = mockWebServerRule.server.takeRequest();
        assertEquals("/", recordedRequest.getPath());
        assertEquals("http://localhost:9900/", recordedRequest.getRequestUrl().toString());
    }

    @Test
    public void testValidQueryName() throws InterruptedException {

        // Setting local server response
        mockWebServerRule.server.enqueue(new MockResponse().setBody(MOCK_RESPONSE));
        // LAUNCH ACTIVITY
        mainActivityTestRule.launchActivity(null);

        RecordedRequest recordedRequest = mockWebServerRule.server.takeRequest();
        assertEquals("getAllTeacher", recordedRequest.getHeaders().get("X-APOLLO-OPERATION-NAME"));
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }
}
