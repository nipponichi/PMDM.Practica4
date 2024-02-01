package com.pmdm.practica3.viewmodel.login;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pmdm.practica3.controller.LoginController;
import com.pmdm.practica3.model.UsuarioModel;

public class LoginViewModel extends ViewModel {

    private final String POST_URL = "http://192.168.2.136/webservice/app/auth.php";
    private LoginController loginController = new LoginController();

    private Context context;
    private UsuarioModel usuarioModel;

    /**
     * Constructor vacío del ViewModel
     */
    public LoginViewModel() {

    }

    /**
     * Envía solicitud de inicio de sesion para usuario
     * @param context
     * @param usuarioModel
     */
    public void doLogin(Context context, UsuarioModel usuarioModel) {
        this.context = context;
        this.usuarioModel = usuarioModel;
        StringRequest stringRequest = loginController.LoginRequest(POST_URL,usuarioModel.getName(),
                usuarioModel.getPasswd(), responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    /**
     * Listener para respuesta esperada
     */
    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            loginController.ControlarLoginResponse(response,context,usuarioModel);
        }
    };

    /**
     * Listener para error de solicitud
     */
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Error.Response", error.toString());
        }
    };
}