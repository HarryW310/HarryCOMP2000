package com.example.harrycomp2000;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.harrycomp2000.Api.ApiService;
import com.example.harrycomp2000.Api.ApiClient;
import com.example.harrycomp2000.models.Employee;


import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ApiService
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Example: Fetch all employees
        apiService.getAllEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Employee employee : response.body()) {
                        Log.d("Employee", employee.getFirstname() + " " + employee.getLastname());
                    }
                } else {
                    Log.e("API Error", "Error fetching employees");
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
            }
        });
    }
}

