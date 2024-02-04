package com.pmdm.practica3.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.NavController;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.pmdm.practica3.model.ClienteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InicioController {

    ArrayList<ClienteModel> clienteModelList = new ArrayList<>();

    public StringRequest ListRequest(String getUrl, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        return new StringRequest(Request.Method.GET, getUrl, responseListener, errorListener);
    }

    /**
     * Verifica la respuesta del WebService
     */
    public void ControlarListResponse(String response, Context context) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {

                procesarListaClientes(jsonResponse.getJSONArray("temp"));
            } else {

                mostrarError(jsonResponse.getString("error"), context);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    /**
     * Creamos la lista de clientes
     * @param jsonArray
     * @throws JSONException
     */
    private void procesarListaClientes(JSONArray jsonArray) throws JSONException {
        //Evita duplicar el listado
        clienteModelList.clear();

        // Creamos clientes con datos del WebService
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonCliente = jsonArray.getJSONObject(i);

            ClienteModel clienteModel = new ClienteModel();

            clienteModel.setId(jsonCliente.getString("id"));
            clienteModel.setNombre(jsonCliente.getString("nombre"));
            clienteModel.setApellidos(jsonCliente.getString("apellidos"));
            clienteModel.setCiudad(jsonCliente.getString("ciudad"));
            clienteModel.setProvincia(jsonCliente.getString("provincia"));
            clienteModel.setTemperatura(jsonCliente.getInt("temperatura"));
            clienteModel.setFormat(jsonCliente.getInt("format"));

            Log.i("Nombre cliente", clienteModel.getNombre());

            clienteModelList.add(clienteModel);

        }
    }

    /**
     * Muestra mensaje de error si el WebService no recibe valores correctos
     */
    private void mostrarError(String error, Context context) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Enviamos el listado de clientes
     */
    public ArrayList<ClienteModel> getClienteModelList() {
        return clienteModelList;
    }
}