package com.pmdm.practica3.ui.localizacion;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.pmdm.practica3.model.ClienteAdapter;
import com.pmdm.practica3.model.ClienteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocalizacionViewModel extends ViewModel {

    private final String GET_URL = "http://192.168.2.136/webservice/app/list.php";

    private Context context;

    private ClienteAdapter mAdapter;

    private ArrayList<ClienteModel> clienteModelList = new ArrayList<>();

    private LocalizacionViewModelCallback callback;


    /**
     * Constructor vacío del ViewModel
     */
    public LocalizacionViewModel() {

    }

    public void setCallback(LocalizacionViewModelCallback callback) {
        this.callback = callback;
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
    public void doList(Context context) {
        this.context = context;

        // Se realiza la solicitud con Volley
        StringRequest stringRequest = ListRequest(GET_URL, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    /**
     * Devuelve la solicitud creada
     *
     * @param getUrl
     * @param responseListener
     * @param errorListener
     * @return
     */
    public StringRequest ListRequest(String getUrl, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        return new StringRequest(Request.Method.GET, getUrl, responseListener, errorListener);
    }

    /**
     * Verifica la respuesta del WebService
     */
    public void controlarListResponse(String response, Context context) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {

                procesarListaClientes(jsonResponse.getJSONArray("temp"));
                callback.onListResponse(clienteModelList);
            } else {

                mostrarError(jsonResponse.getString("error"), context);
                callback.onError(jsonResponse.getString("error"));
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


    /**
     * Creamos la lista de clientes
     *
     * @param jsonArray
     * @throws JSONException
     */
    private ArrayList<ClienteModel> procesarListaClientes(JSONArray jsonArray) throws JSONException {
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

            Log.i("cliente viewModLoc", clienteModel.getNombre());

            clienteModelList.add(clienteModel);

        }
        return clienteModelList;
    }

    /**
     * Listener para respuesta esperada
     */
    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // Procesa la respuesta y actualiza el adaptador
            controlarListResponse(response, context);
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

    /**
     * Muestra mensaje de error si el WebService no recibe valores correctos
     */
    private void mostrarError(String error, Context context) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Interfaz callbakc para controlar la carga desde WebService
     */
    public interface LocalizacionViewModelCallback {
        void onListResponse(ArrayList<ClienteModel> clienteModelList);

        void onError(String error);
    }
}
