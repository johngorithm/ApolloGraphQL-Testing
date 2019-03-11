package com.jxw.graphql.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.jxw.graphql.GetAllTeacherQuery;
import com.jxw.graphql.R;
import com.jxw.graphql.app.DemoApplication;
import com.jxw.graphql.service.ApiService;
import com.jxw.graphql.utils.EspressoIdlingResource;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    TextView firstTeacherName;
    TextView secondTeacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencing our view components
        firstTeacherName = findViewById(R.id.tv_first_teacher_name);
        secondTeacherName = findViewById(R.id.tv_second_teacher_name);

        // Casting our app's context onto our demo application
        DemoApplication app = (DemoApplication) getApplication();
        // fetches all available teachers
        getAllTeacher(app);
    }

    // Fetches all available teachers
    public void getAllTeacher(DemoApplication app) {
        EspressoIdlingResource.increment();

        ApiService.getAppApolloClient(app).query(GetAllTeacherQuery.builder().build())
                .enqueue(new ApolloCall.Callback<GetAllTeacherQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetAllTeacherQuery.Data> response) {

                        runOnUiThread(() -> {
                            if (response.hasErrors()) {
                                firstTeacherName.setText(response.errors().get(0).message());
                                secondTeacherName.setVisibility(View.GONE);
                            } else {
                                firstTeacherName.setText(response.data().allTeachers().get(0).name());
                                secondTeacherName.setText(response.data().allTeachers().get(1).name());
                            }
                            EspressoIdlingResource.decrement();
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        runOnUiThread(() -> {
                            firstTeacherName.setText(e.getMessage());
                            secondTeacherName.setVisibility(View.GONE);

                            EspressoIdlingResource.decrement();
                        });
                    }
                });
    }
}
