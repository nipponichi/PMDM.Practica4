package com.pmdm.practica3.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.practica3.R;
import com.pmdm.practica3.viewmodel.resumen.ResumenActivity;

import java.util.ArrayList;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {
    private ArrayList<ClienteModel> clienteModelArrayList;
    private LayoutInflater lInflater;
    private Context context;

    /**
     * Constructor
     *
     * @param clienteModelArrayList
     * @param context
     */
    public ClienteAdapter(ArrayList<ClienteModel> clienteModelArrayList, Context context) {
        this.lInflater = LayoutInflater.from(context);
        this.context = context;
        this.clienteModelArrayList = clienteModelArrayList;
        Log.i("ClienteAdapter Constructor", "Creado");
    }

    @Override
    public int getItemCount() {
        // Retorna el tamaño del array
        return clienteModelArrayList.size();
    }

    /**
     * Crea la vista para cada elemento del recyclerView
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = lInflater.inflate(R.layout.cliente_item, null);
        return new ClienteAdapter.ViewHolder(view);
    }

    /**
     * Añade los datos del cliente en la posicion indicada
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ClienteAdapter.ViewHolder holder, int position) {
        Log.i("onBindViewHolder", "posicion: " + position);
        holder.bindData(clienteModelArrayList.get(position));
    }

    /**
     * Actualiza y notifica cambios en el listado
     *
     * @param newClienteModelList
     */
    public void updateData(ArrayList<ClienteModel> newClienteModelList) {
        this.clienteModelArrayList = newClienteModelList;
        notifyDataSetChanged();
    }

    /**
     * Crea la vista de cada cliente
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, temperatura, poblacion;

        /**
         * Inicia los campos de la vista
         *
         * @param viewItem
         */
        ViewHolder(View viewItem) {
            super(viewItem);
            nombre = viewItem.findViewById(R.id.textViewNombre);
            temperatura = viewItem.findViewById(R.id.textViewTemperatura);
            poblacion = viewItem.findViewById(R.id.textViewPoblacion);
        }

        /**
         * Añade los datos a las vistas
         *
         * @param clienteModel
         */
        void bindData(ClienteModel clienteModel) {
            nombre.setText(clienteModel.getNombre() + " " + clienteModel.getApellidos());

            // Obtiene el formato de las SharedPreferences
            int confFormat = getSavedFormat();
            int temp = clienteModel.getTemperatura();
            int format = clienteModel.getFormat();

            // Si el formato guardado no coincide con el formato del ClienteModel, cambia la temperatura
            if (format != confFormat) {
                temp = changeTempUnit(temp, format);
                format = confFormat;
            }

            temperatura.setTextColor(changeButtonColor(temp, format));
            temperatura.setText(String.valueOf(temp));
            poblacion.setText(clienteModel.getCiudad());

            // Establece una función de click para ir a vista resumen con el cliente seleccionado
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ResumenActivity.class);
                    intent.putExtra("id", Integer.valueOf(clienteModel.getId()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            });
        }

        /**
         * Cambia el color del parámetro temperatura
         *
         * @param temperatura
         * @param format
         * @return
         */
        private int changeButtonColor(int temperatura, int format) {
            int temp = Integer.valueOf(temperatura);
            if ((temp > 38 && format == 1) || (temp > 100 && format == 2)) {
                return Color.RED;
            }
            return Color.BLACK;
        }
    }

    // Obtiene el formato de temperatura guardado en SharedPreferences
    private int getSavedFormat() {
        SharedPreferences sharedPref = context.getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        boolean celsiusActivo = sharedPref.getBoolean("celsiusActivo", true);
        Log.i("CelsiusActivo", String.valueOf(celsiusActivo));
        if (celsiusActivo) {
            return 1;
        } else {
            return 2;
        }
    }

    // Convierte la temperatura de Celsius a Fahrenheit y viceversa
    private int changeTempUnit(int temp, int format) {
        if (format == 1) {
            // Convertir de Celsius a Fahrenheit
            return (int) ((temp * 9.0 / 5.0) + 32);
        } else {
            // Convertir de Fahrenheit a Celsius
            return (int) ((temp - 32) * 5.0 / 9.0);
        }
    }

}

