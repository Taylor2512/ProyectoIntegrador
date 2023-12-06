/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.almacenamiento.Models.Dtos;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author jt251
 */
public class ApiResponsebase {

    private boolean success;
    private int statusCode;
    private String message;
    private List<String> errors;

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ApiResponsebase{");
        sb.append("success=").append(success);
        sb.append(", statusCode=").append(statusCode);
        sb.append(", message=").append(message);
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.success ? 1 : 0);
        hash = 89 * hash + this.statusCode;
        hash = 89 * hash + Objects.hashCode(this.message);
        hash = 89 * hash + Objects.hashCode(this.errors);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApiResponsebase other = (ApiResponsebase) obj;
        if (this.success != other.success) {
            return false;
        }
        if (this.statusCode != other.statusCode) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        return Objects.equals(this.errors, other.errors);
    }

   

    public ApiResponsebase(boolean success, int statusCode, String message, List<String> errors) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }

    public ApiResponsebase() {
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

