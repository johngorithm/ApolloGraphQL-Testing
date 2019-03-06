package com.jxw.graphql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.jxw.graphql.service.AppApolloClient;
import com.jxw.graphql.utils.EspressoIdlingResource;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    TextView username;
    TextView firstTeacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.tv_teacher_name);
        firstTeacherName = findViewById(R.id.tv_first_teacher_name);
        DemoApplication app = (DemoApplication) getApplication();
        getAllTeacher(app);
    }

    // Get all Teachers
    public void getAllTeacher(DemoApplication app) {
        EspressoIdlingResource.increment();
        AppApolloClient.getAppApolloClient(app).query(GetAllTeacherQuery.builder().build())
                .enqueue(new ApolloCall.Callback<GetAllTeacherQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetAllTeacherQuery.Data> response) {

                        runOnUiThread(() -> {
                            if (response.hasErrors()) {
                                firstTeacherName.setText(response.errors().get(0).message());
                            } else {
                                firstTeacherName.setText(response.data().allTeachers().get(0).name());
                                username.setText(response.data().allTeachers().get(1).name());
                            }
                            EspressoIdlingResource.decrement();
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        runOnUiThread(() -> {
                            firstTeacherName.setText(e.getMessage());
                            EspressoIdlingResource.decrement();
                        });
                    }
                });
    }
}
