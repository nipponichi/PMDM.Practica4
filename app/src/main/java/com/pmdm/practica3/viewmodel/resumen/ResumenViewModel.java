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
import com.pmdm.practica3.controller.ResumenController;
import com.pmdm.practica3.model.ClienteModel;

public class ResumenViewModel  extends ViewModel {
    private ResumenController resumenController = new ResumenController();
    private ResumenActivity resumenActivity;
    private final String GET_URL = "http://192.168.2.136/webservice/app/listid.php";
    private Context context;

    private ClienteModel clienteModel;

    private MutableLiveData<ClienteModel> clienteModelLiveData = new MutableLiveData<>();

    public LiveData<ClienteModel> getClienteModelLiveData() {
        return clienteModelLiveData;
    }

    public void doResume (Context context, int id) {
        this.context = context;
        StringRequest tempRequest = resumenController.resumeRequest(GET_URL, id, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(tempRequest);
    }

    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            clienteModel = resumenController.ControlarResumeResponse(response, context);
            clienteModelLiveData.setValue(clienteModel);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Error.Response", error.toString());
        }
    };


}
