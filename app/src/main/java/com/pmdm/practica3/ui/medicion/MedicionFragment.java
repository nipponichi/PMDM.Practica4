package com.pmdm.practica3.ui.medicion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.pmdm.practica3.databinding.FragmentMedicionBinding;
import com.pmdm.practica3.model.ClienteModel;


public class MedicionFragment extends Fragment {

    private FragmentMedicionBinding binding;
    private ImageView imageView3;
    private TextView tvMedicionTemperatura;
    private EditText etNombre, etApellidos, etTemperatura, etPoblacion, etProvincia;
    private RadioGroup rgSelector;
    private RadioButton rbCelsius, rbFahrenheit;
    private Button btnFinalizar;

    private String nombre, apellidos, temperatura, poblacion, provincia;

    private boolean celsius, fahrenheit;

    private Bundle bundle;

    private MedicionViewModel medicionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicionViewModel =
                new ViewModelProvider(this).get(MedicionViewModel.class);

        binding = FragmentMedicionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setButton();

        return root;
    }

    /**
     * Set de parámetros a la vista
     */
    private void setButton() {
        imageView3 = binding.imageView3;
        tvMedicionTemperatura = binding.tvMedicionTemperatura;
        etNombre = binding.etNombre;
        etApellidos = binding.etApellidos;
        etTemperatura = binding.etTemperatura;
        etPoblacion = binding.etPoblacion;
        etProvincia = binding.etProvincia;
        rgSelector = binding.rgSelector;
        rbCelsius = binding.rbCelsius;
        rbFahrenheit = binding.rbFahrenheit;
        btnFinalizar = binding.btnFinalizar;
        btnFinalizar.setOnClickListener(doTemp);
    }

    /**
     * Escucha para recoger datos de cliente
     */
    public final View.OnClickListener doTemp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Recoge valores de los campos de entrada
            nombre = String.valueOf(etNombre.getText()).trim();
            apellidos = String.valueOf(etApellidos.getText()).trim();
            temperatura = String.valueOf(etTemperatura.getText()).trim();
            poblacion = String.valueOf(etPoblacion.getText()).trim();
            provincia = String.valueOf(etProvincia.getText()).trim();
            celsius = rbCelsius.isChecked();
            fahrenheit = rbFahrenheit.isChecked();

            // Validación del formulario
            if (checkForm(nombre, apellidos, temperatura, poblacion, provincia)) {
                ClienteModel clienteModel = new ClienteModel();
                clienteModel.setNombre(nombre);
                clienteModel.setApellidos(apellidos);
                clienteModel.setTemperatura(Integer.valueOf(temperatura));
                clienteModel.setCiudad(poblacion);
                clienteModel.setProvincia(provincia);
                clienteModel.setFormat(clienteModel.formatChooser(celsius));
                medicionViewModel.doTemp(getContext(), clienteModel);
            }
        }
    };

    /**
     * Control de errores de formulario
     */
    public boolean checkForm(String nombre, String apellidos, String temperatura,
                             String poblacion, String provincia) {

        if ("".equals(nombre) || "".equals(apellidos) || "".equals(temperatura)
                || "".equals(poblacion) || "".equals(provincia)) {
            Toast.makeText(getContext(), "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!temperatura.matches(".*\\d.*")) {
            Toast.makeText(getContext(), "Por favor, inserte valores numéricos en temperatura", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Libera recursos
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}