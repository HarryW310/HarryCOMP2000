package com.example.harrycomp2000.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;
import com.example.harrycomp2000.models.Employee;
import com.example.harrycomp2000.models.ApiResponse;

public interface ApiService {

    @GET("employees")
    Call<List<Employee>> getAllEmployees();

    @GET("employees/get/{id}")
    Call<Employee> getEmployeeById(@Path("id") int id);

    @POST("employees/add")
    Call<ApiResponse> addEmployee(@Body Employee employee);

    @PUT("employees/edit/{id}")
    Call<ApiResponse> updateEmployee(@Path("id") int id, @Body Employee employee);

    @DELETE("employees/delete/{id}")
    Call<ApiResponse> deleteEmployee(@Path("id") int id);

    @GET("health")
    Call<ApiResponse> healthCheck();
}