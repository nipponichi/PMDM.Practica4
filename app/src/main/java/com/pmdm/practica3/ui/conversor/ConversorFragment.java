package com.pmdm.practica3.ui.conversor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pmdm.practica3.databinding.FragmentConversorBinding;

public class ConversorFragment extends Fragment {

    private Button btnCalcular1, btnCalcular2;

    private EditText etCelsius1, etCelsius2, etFahrenheit1, etFahrenheit2;
    private FragmentConversorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConversorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setVariables();

        return root;
    }

    /**
     * Set variables de la vista con parámetros de clase
     */
    private void setVariables(){
        btnCalcular1 = binding.btnCalcular1;
        btnCalcular2 = binding.btnCalcular2;
        etCelsius1 = binding.etCelsius1;
        etCelsius2 = binding.etCelsius2;
        etFahrenheit1 = binding.etFahrenheit1;
        etFahrenheit2 = binding.etFahrenheit2;

        btnCalcular1.setOnClickListener(calcularCF);
        btnCalcular2.setOnClickListener(calcularFC);
    }

    /**
     * Cálculo Celsius a Fahrenheit
     */
    private final View.OnClickListener calcularCF = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Double celsius = 0.0;
            String celsiusStr = String.valueOf(etCelsius1.getText()).trim();
            //Controla introducción de caracteres no numéricos
            try {
                celsius = Double.parseDouble(celsiusStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(),"Introduce un número", Toast.LENGTH_SHORT).show();
            }
            Double resultado = ((celsius * 9) / 5) + 32;
            Math.round(resultado);
            etFahrenheit2.setText(String.valueOf(resultado));
        }
    };

    /**
     * Cálculo Fahrenheit a Celsius
     */
    private final View.OnClickListener calcularFC = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Double fahrenheit = 0.0;
            String fahrenheitStr = String.valueOf(etFahrenheit1.getText()).trim();
            //Controla introducción de caracteres no numéricos
            try {
                fahrenheit = Double.parseDouble(fahrenheitStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(),"Introduce un número", Toast.LENGTH_SHORT).show();
            }
            Double resultado = ((fahrenheit - 32) * 5) / 9;
            Math.round(resultado);
            etCelsius2.setText(String.valueOf(resultado));
        }
    };

    /**
     * Limpia recursos del fragment que se cierra
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}