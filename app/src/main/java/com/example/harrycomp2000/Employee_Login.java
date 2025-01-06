package com.example.harrycomp2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.harrycomp2000.Api.ApiService;
import com.example.harrycomp2000.Api.ApiClient;
import com.example.harrycomp2000.models.Employee;


import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getAllEmployees().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Employee employee : response.body()) {
                        Log.d("Employee", employee.getFirstname() + " " + employee.getLastname());
                    }
                } else {
                    Log.e("API Error", "Error fetching employees");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                Log.e("API Failure", Objects.requireNonNull(t.getMessage()));
            }
        });

    }
}

