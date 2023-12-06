/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.almacenamiento.Services;

/**
 *
 * @author jt251
 */
 

import java.util.List;

public class Almacenamiento {
    private List<String> data; // Supongamos que almacenamos una lista de strings

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
    
    // Métodos CRUD básicos
    public void crear(String nuevoDato) {
        data.add(nuevoDato);
    }

    public List<String> leer() {
        return data;
    }

    public void actualizar(int indice, String nuevoDato) {
        data.set(indice, nuevoDato);
    }

    public void eliminar(int indice) {
        data.remove(indice);
    }
}
