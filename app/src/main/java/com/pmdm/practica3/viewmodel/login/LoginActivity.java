package com.pmdm.practica3.viewmodel.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.pmdm.practica3.R;
import com.pmdm.practica3.databinding.ViewLoginBinding;
import com.pmdm.practica3.model.UsuarioModel;
import com.pmdm.practica3.sharedPreferences.SharedConstants;


public class LoginActivity extends AppCompatActivity {
    private ViewLoginBinding binding;
    private UsuarioModel usuarioModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ViewLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(SharedConstants.PREF_NAME, Context.MODE_PRIVATE);

        // Verifica el estado de cbRecordar y carga datos guardados si es necesario
        boolean rememberChecked = sharedPreferences.getBoolean(SharedConstants.KEY_REMEMBER, false);
        binding.cbRecordar.setChecked(rememberChecked);
        if (rememberChecked) {
            String savedUser = sharedPreferences.getString(SharedConstants.KEY_USER, "");
            String savedPasswd = sharedPreferences.getString(SharedConstants.KEY_PASSWD, "");
            binding.tiUser.setText(savedUser);
            binding.tiPassword.setText(savedPasswd);
        }

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUser = binding.tiUser.getText().toString().trim();
                String textPasswd = binding.tiPassword.getText().toString().trim();
                usuarioModel = new UsuarioModel(textUser, textPasswd);

                // Guarda datos en sharedpreferences si cbRemember está marcado
                if (binding.cbRecordar.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SharedConstants.KEY_USER, textUser);
                    editor.putString(SharedConstants.KEY_PASSWD, textPasswd);
                    editor.putBoolean(SharedConstants.KEY_REMEMBER, true);
                    editor.apply();
                } else {
                    // Limpia datos en sharedpreferences si el checkbox no está marcado
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(SharedConstants.KEY_USER);
                    editor.remove(SharedConstants.KEY_PASSWD);
                    editor.remove(SharedConstants.KEY_REMEMBER);
                    editor.apply();
                }
                // Inicia el login de usuario
                loginViewModel.doLogin(LoginActivity.this, usuarioModel);
            }
        });
    }

    /**
     * Libera recursos
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}


