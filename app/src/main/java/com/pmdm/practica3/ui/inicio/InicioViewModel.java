package com.pmdm.practica3.ui.inicio;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pmdm.practica3.constants.Constants;
import com.pmdm.practica3.controller.InicioController;
import com.pmdm.practica3.model.ClienteAdapter;
import com.pmdm.practica3.model.ClienteModel;

import java.util.ArrayList;

public class InicioViewModel extends ViewModel {

    private final String getUrl = Constants.LIST_PHP;
    private InicioController inicioController = new InicioController();

    private Context context;

    private ClienteAdapter mAdapter;

    /**
     * Constructor vacío del ViewModel
     */
    public InicioViewModel() {

    }

    /**
     * Se establece el adaptador
     *
     * @param adapter
     */
    public void setAdapter(ClienteAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * Obtiene la lista de clientes
     *
     * @param context
     * @return
     */
    public ArrayList<ClienteModel> doList(Context context) {
        this.context = context;

        //Se realiza la solicitud con Volley
        StringRequest stringRequest = inicioController.ListRequest(getUrl, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        return inicioController.getClienteModelList();
    }

    /**
     * Listener para respuesta esperada
     */
    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // Procesa la respuesta y actualiza el adaptador
            inicioController.ControlarListResponse(response, context);
            mAdapter.updateData(inicioController.getClienteModelList());
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