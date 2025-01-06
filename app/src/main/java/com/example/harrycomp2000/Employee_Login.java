package com.example.harrycomp2000;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harrycomp2000.Api.ApiClient;
import com.example.harrycomp2000.Api.ApiService;
import com.example.harrycomp2000.models.Employee;
import com.example.harrycomp2000.models.LoginRequest;
import com.example.harrycomp2000.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee_Login extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private List<Integer> validEmployeeIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);

        usernameEditText = findViewById(R.id.EmployeeUsername);
        passwordEditText = findViewById(R.id.EmployeePassword);
        loginButton = findViewById(R.id.EmployeeButton);

        fetchEmployeeIDsFromAPI();

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                login(username, password);
            }
        });
    }

    private void fetchEmployeeIDsFromAPI() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getAllEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Employee employee : response.body()) {
                        validEmployeeIDs.add(employee.getId());
                        Log.d("Employee ID", "Fetched ID: " + employee.getId());
                    }
                    Log.d("API Success", "Employee IDs fetched: " + validEmployeeIDs);
                } else {
                    Log.e("API Error", "Failed to fetch employee IDs. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.e("API Failure", "Error fetching employee IDs: " + t.getMessage());
                Toast.makeText(Employee_Login.this, "Unable to fetch employee IDs", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login(String username, String password) {
        try {
            int employeeID = Integer.parseInt(username);

            if (validEmployeeIDs.contains(employeeID) && password.equals("WorkFlow")) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Employee_Login.this, Employee_Home.class);
                intent.putExtra("employeeID", employeeID); // Pass employee ID to the next activity
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid employee ID or password", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Employee ID must be a number", Toast.LENGTH_SHORT).show();
        }
    }
}




