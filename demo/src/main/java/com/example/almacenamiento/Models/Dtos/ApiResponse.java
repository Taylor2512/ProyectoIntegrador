/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.almacenamiento.Models.Dtos;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author jt251
 * @param <T>
 */
public class ApiResponse<T> extends ApiResponsebase {
    private T data;

    public ApiResponse(T data, boolean success, int statusCode, String message, List<String> errors) {
        super(success, statusCode, message, errors);
        this.data = data;
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ApiResponse{");
        sb.append("data=").append(data);
        sb.append('}');
        return sb.toString();
    }
 
}