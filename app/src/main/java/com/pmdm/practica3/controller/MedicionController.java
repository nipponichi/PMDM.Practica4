package com.pmdm.practica3.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import com.pmdm.practica3.viewmodel.resumen.ResumenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedicionController {

    /**
     * Envia de datos de cliente a WebService
     */
    public StringRequest tempRequest(String postUrl, String nombre, String apellidos, int temperatura, int format,
                                     String ciudad, String provincia,
                                     Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("apellidos", apellidos);
        params.put("temperatura", String.valueOf(temperatura));
        params.put("format", String.valueOf(format));
        params.put("ciudad", ciudad);
        params.put("provincia", provincia);

        return new StringRequest(Request.Method.POST, postUrl, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
    }

    /**
     * Controla la respuesta del WebService
     *
     * @param response
     * @param context
     */
    public void ControlarTempResponse(String response, Context context) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            Log.d("ID cliente: ", jsonResponse.getString("temp"));
            int id = jsonResponse.getInt("temp");
            if (success) {
                Intent intent = new Intent(context, ResumenActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            } else {
                String error = jsonResponse.getString("error");
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
