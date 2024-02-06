package com.pmdm.practica3.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.pmdm.practica3.MainMenu;
import com.pmdm.practica3.model.UsuarioModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginController {

    // Crea una solicitud de inicio de sesión
    public StringRequest LoginRequest(String postUrl, String user, String password,
                                      Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        // Almacena los parámetros de usuario
        Map<String, String> params = new HashMap<>();
        Log.i("user/passwd StringRequest", user + "/" + password);
        params.put("user", user);
        params.put("passwd", password);


        return new StringRequest(Request.Method.POST, postUrl, responseListener, errorListener) {
            @Override
            public Map<String, String> getParams() {
                return params;
            }
        };
    }

    // Maneja la respuesta del WebService
    public void ControlarLoginResponse(String response, Context context, UsuarioModel usuarioModel) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            Log.d("Response", response.toString());
            if (success) {
                Toast.makeText(context, "Entrando al sistema...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("UsuarioModel", usuarioModel);
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
