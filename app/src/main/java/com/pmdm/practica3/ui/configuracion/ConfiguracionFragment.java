package com.pmdm.practica3.ui.configuracion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pmdm.practica3.MainMenu;
import com.pmdm.practica3.databinding.FragmentConfiguracionBinding;
import com.pmdm.practica3.model.UsuarioModel;
import com.pmdm.practica3.sharedPreferences.SharedConstants;

public class ConfiguracionFragment extends Fragment {

    private Button btnGuardar;

    boolean celsius;
    private Switch swCelsius, swFahrenheit;

    private String usuario, passwd;


    private FragmentConfiguracionBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        celsius = sharedPref.getBoolean("celsiusActivo", true);
        setVariables();

        return root;
    }

    /**
     * Set variables de la vista con parámetros de clase
     */
    private void setVariables() {
        swCelsius = binding.swCelsius;
        swFahrenheit = binding.swFahrenheit;
        btnGuardar = binding.btnGuardar;

        btnGuardar.setOnClickListener(goToMenu);
        swFahrenheit.setOnClickListener(fahrenheitActivo);
        swCelsius.setOnClickListener(celsiusActivo);

        // Muestra en inicio el valor guardado por el usuario
        if (celsius) {
            swCelsius.setChecked(true);
            swFahrenheit.setChecked(false);
        } else {
            swCelsius.setChecked(false);
            swFahrenheit.setChecked(true);
        }
    }

    /**
     * Ir a MainMenu
     */
    private final View.OnClickListener goToMenu = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), MainMenu.class);

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
        SharedPreferences shared = getActivity().getSharedPreferences(SharedConstants.PREF_NAME, Context.MODE_PRIVATE);
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

    /**
     * Al menos un Switch debe estar activo
     */
    private final View.OnClickListener celsiusActivo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (swCelsius.isChecked()) {
                swFahrenheit.setChecked(false);
                guardaPreferences(true, false);
            }
            if (!swCelsius.isChecked()) {
                swFahrenheit.setChecked(true);
                guardaPreferences(false, true);
            }
        }
    };

    /**
     * Al menos un Switch debe estar activo
     */
    private final View.OnClickListener fahrenheitActivo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (swFahrenheit.isChecked()) {
                swCelsius.setChecked(false);
                guardaPreferences(false, true);
            }
            if (!swFahrenheit.isChecked()) {
                swCelsius.setChecked(true);
                guardaPreferences(true, false);
            }
        }
    };

    /**
     * Guarda la configuración de la unidad de temperatura
     *
     * @param celsius
     * @param fahrenheit
     */
    private void guardaPreferences(boolean celsius, boolean fahrenheit) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        Log.i("celsius", String.valueOf(celsius));
        editor.putBoolean("celsiusActivo", celsius);
        editor.putBoolean("fahrenheitActivo", fahrenheit);
        editor.apply();
    }

    /**
     * Limpia recursos del fragment que se cierra
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}