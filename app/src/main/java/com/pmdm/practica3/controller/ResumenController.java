package com.pmdm.practica3.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.pmdm.practica3.model.ClienteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResumenController {

    /**
     * Solicitud de datos cliente a WebService
     */
    public StringRequest resumeRequest(String baseUrl, int id,
                                       Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String url = baseUrl + "?id=" + id;

        Log.i("URL resumeRequest", url);

        return new StringRequest(Request.Method.GET, url, responseListener, errorListener);
    }

    /**
     * Controla la respuesta del servidor
     */
    public ClienteModel ControlarResumeResponse(String response, Context context) {
        ClienteModel clienteModel = null;
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            Log.d("Response", response);

            if (success) {
                JSONArray tempArray = jsonResponse.getJSONArray("temp");

                if (tempArray.length() > 0) {
                    JSONObject jsonCliente = tempArray.getJSONObject(0);
                    clienteModel = new ClienteModel();
                    clienteModel.setId(jsonCliente.getString("id"));
                    clienteModel.setNombre(jsonCliente.getString("nombre"));
                    clienteModel.setApellidos(jsonCliente.getString("apellidos"));
                    clienteModel.setCiudad(jsonCliente.getString("ciudad"));
                    clienteModel.setProvincia(jsonCliente.getString("provincia"));
                    clienteModel.setTemperatura(jsonCliente.getInt("temperatura"));
                    clienteModel.setFormat(jsonCliente.getInt("format"));
                    Log.i("Nombre cliente", clienteModel.getNombre());
                }
            } else {
                String error = jsonResponse.getString("error");
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(context, "No se pudieron cargar los datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return clienteModel;
    }
}