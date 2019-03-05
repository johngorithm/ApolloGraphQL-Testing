package com.jxw.graphql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.jxw.graphql.service.AppApolloClient;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.tv_teacher_name);
        getAllTeacher();
    }


    // Get all Teachers
    public void getAllTeacher() {
        AppApolloClient.getAppApolloClient().query(GetAllTeacherQuery.builder().build())
                .enqueue(new ApolloCall.Callback<GetAllTeacherQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetAllTeacherQuery.Data> response) {
                        Log.d("SUCCESS", "onResponse: "+response.data().allTeachers);
                        runOnUiThread(() -> username.setText(response.data().allTeachers().get(0).name()));
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.d("********", "onFailure: "+e.getMessage());

                    }
                });
    }
}
