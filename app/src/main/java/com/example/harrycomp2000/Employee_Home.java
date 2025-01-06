package com.example.harrycomp2000;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.harrycomp2000.Api.ApiClient;
import com.example.harrycomp2000.Api.ApiService;
import com.example.harrycomp2000.models.Employee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        int employeeID = getIntent().getIntExtra("employeeID", -1);

        TextView usernameTextView = findViewById(R.id.textView2);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.getEmployeeById(employeeID).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Employee employee = response.body();
                    String fullName = employee.getFirstname() + " " + employee.getLastname();

                    usernameTextView.setText(fullName);
                } else {
                    usernameTextView.setText("User not found");
                    Toast.makeText(Employee_Home.this, "Failed to fetch employee details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                usernameTextView.setText("Error loading user");
                Toast.makeText(Employee_Home.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}