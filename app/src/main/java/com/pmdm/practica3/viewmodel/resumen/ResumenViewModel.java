package com.pmdm.practica3.viewmodel.resumen;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pmdm.practica3.constants.Constants;
import com.pmdm.practica3.controller.ResumenController;
import com.pmdm.practica3.model.ClienteModel;

public class ResumenViewModel  extends ViewModel {
    private ResumenController resumenController = new ResumenController();
    private ResumenActivity resumenActivity;
    private String getUrl = Constants.LISTID_PHP;
    private Context context;

    private ClienteModel clienteModel;

    private MutableLiveData<ClienteModel> clienteModelLiveData = new MutableLiveData<>();

    public LiveData<ClienteModel> getClienteModelLiveData() {
        return clienteModelLiveData;
    }

    /**
     * Solicitud de carga los datos de usuario desde base de datos a vista resumen
     * @param context
     * @param id
     */
    public void doResume (Context context, int id) {
        this.context = context;
        StringRequest tempRequest = resumenController.resumeRequest(getUrl, id, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(tempRequest);
    }

    /**
     * Escucha el éxito de la solicitud y actualiza el livedata.
     */
    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            clienteModel = resumenController.ControlarResumeResponse(response, context);
            // Notifica que los datos del cliente cambian, a modo de callback
            clienteModelLiveData.setValue(clienteModel);
        }
    };

    /**
     * Escucha del error de la solicitud.
     */
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Error.Response", error.toString());
        }
    };

}
