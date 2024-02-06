package com.pmdm.practica3.viewmodel.resumen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pmdm.practica3.MainMenu;

import com.pmdm.practica3.databinding.ActivityResumenBinding;
import com.pmdm.practica3.model.ClienteModel;
import com.pmdm.practica3.model.UsuarioModel;
import com.pmdm.practica3.sharedPreferences.SharedConstants;

public class ResumenActivity extends AppCompatActivity {

    private ActivityResumenBinding binding;
    private TextView tvNombre, tvApellidos, tvGrados, tvPoblacion, tvProvincia;
    private Button btnMenu, btnColor;

    String usuario, passwd;
    private ResumenViewModel resumenViewModel;


    /**
     * Muestra datos de los clientes registrados
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);

        super.onCreate(savedInstanceState);

        resumenViewModel =
                new ViewModelProvider(this).get(ResumenViewModel.class);

        binding = ActivityResumenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtiene id del cliente a través del Intent
        Intent intent = getIntent();
        int id = (int) intent.getSerializableExtra("id");

        // Solicitud de datos a WebService
        resumenViewModel.doResume(this, id);

        // Configura la interfaz de la vista resumen
        setViewParameters();

        // Observa los cambios del ViewModel
        resumenViewModel.getClienteModelLiveData().observe(this, new Observer<ClienteModel>() {
            @Override
            public void onChanged(ClienteModel clienteModel) {
                if (clienteModel != null) {
                    setParameters(clienteModel);
                }
            }
        });

        btnColor.setEnabled(false);
        btnMenu.setOnClickListener(goToMenu);
    }

    private void setViewParameters() {
        tvNombre = binding.tvNombre;
        tvApellidos = binding.tvApellidos;
        tvGrados = binding.tvGrados;
        tvPoblacion = binding.tvPoblacion;
        tvProvincia = binding.tvProvincia;
        btnMenu = binding.btnMenu;
        btnColor = binding.btnColor;
    }

    /**
     * Escribe variables en pantalla
     *
     * @param clienteModel
     */
    private void setParameters(ClienteModel clienteModel) {
        tvNombre.setText(clienteModel.getNombre());
        tvApellidos.setText(clienteModel.getApellidos());
        tvGrados.setText(String.valueOf(clienteModel.getTemperatura()));
        changeButtonColor(clienteModel.getTemperatura(), clienteModel.getFormat());
        tvPoblacion.setText(clienteModel.getCiudad());
        tvProvincia.setText(clienteModel.getProvincia());
    }

    /**
     * Cambio color boton segun temperatura
     *
     * @param temperatura
     * @param format
     */
    private void changeButtonColor(int temperatura, int format) {
        if ((temperatura > 38 && format == 1) || (temperatura > 100 && format == 2)) {
            btnColor.setBackgroundColor(Color.parseColor("#EC0000"));
        }
    }


    private final View.OnClickListener goToMenu = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ResumenActivity.this, MainMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            UsuarioModel usuarioModel;
            usuarioModel = checkParametros();
            intent.putExtra("UsuarioModel", usuarioModel);
            startActivity(intent);
        }
    };

    /**
     * Añade los valores del usuario
     *
     * @return
     */
    public UsuarioModel checkParametros() {
        UsuarioModel usuarioModel;
        SharedPreferences shared = this.getSharedPreferences(SharedConstants.PREF_NAME, Context.MODE_PRIVATE);
        usuario = shared.getString(SharedConstants.KEY_USER, "");
        passwd = shared.getString(SharedConstants.KEY_PASSWD, "");

        if (usuario == "" || passwd == "") {
            usuario = "default";
            passwd = "default";
            usuarioModel = new UsuarioModel(usuario, passwd);
        } else {
            usuarioModel = new UsuarioModel(usuario, passwd);
        }

        return usuarioModel;
    }


}