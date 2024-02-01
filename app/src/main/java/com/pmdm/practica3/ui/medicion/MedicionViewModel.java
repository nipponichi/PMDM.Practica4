package com.pmdm.practica3.ui.medicion;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pmdm.practica3.controller.MedicionController;
import com.pmdm.practica3.model.ClienteModel;



public class MedicionViewModel extends ViewModel {

    private final String POST_URL = "http://192.168.2.136/webservice/app/temp.php";
    private MedicionController medicionController = new MedicionController();
    private ClienteModel clienteModel = new ClienteModel();
    private Context context;

    /**
     * Constructor vacío
     */
    public MedicionViewModel() {

    }

    /**
     * Hace petición de guardado de cliente
     * @param context
     * @param clienteModel
     */
    public void doTemp(Context context, ClienteModel clienteModel) {
        this.context = context;
        this.clienteModel = clienteModel;

        // Tomamos los datos del cliente
        StringRequest stringRequest = medicionController.tempRequest(POST_URL, clienteModel.getNombre(),
                clienteModel.getApellidos(), clienteModel.getTemperatura(), clienteModel.getFormat(),
                clienteModel.getCiudad(), clienteModel.getProvincia(), responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    /**
     * Listener para respuesta esperada
     */
    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            medicionController.ControlarTempResponse(response, context);
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