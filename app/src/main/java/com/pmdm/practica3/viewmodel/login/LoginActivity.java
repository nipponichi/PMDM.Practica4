package com.pmdm.practica3.viewmodel.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.pmdm.practica3.databinding.ViewLoginBinding;
import com.pmdm.practica3.model.UsuarioModel;


public class LoginActivity extends AppCompatActivity {
    private ViewLoginBinding binding;
    private UsuarioModel usuarioModel;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "login_prefs";
    private static final String KEY_USER = "user";
    private static final String KEY_PASSWD = "passwd";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //EdgeToEdge.enable(this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ViewLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Verifica el estado de cbRemember y carga datos guardados si es necesario
        boolean rememberChecked = sharedPreferences.getBoolean(KEY_REMEMBER, false);
        binding.cbRecordar.setChecked(rememberChecked);
        if (rememberChecked) {
            String savedUser = sharedPreferences.getString(KEY_USER, "");
            String savedPasswd = sharedPreferences.getString(KEY_PASSWD, "");
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
                    editor.putString(KEY_USER, textUser);
                    editor.putString(KEY_PASSWD, textPasswd);
                    editor.putBoolean(KEY_REMEMBER, true);
                    editor.apply();
                } else {
                    // Limpia datos en sharedpreferences si el checkbox no está marcado
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_USER);
                    editor.remove(KEY_PASSWD);
                    editor.remove(KEY_REMEMBER);
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


