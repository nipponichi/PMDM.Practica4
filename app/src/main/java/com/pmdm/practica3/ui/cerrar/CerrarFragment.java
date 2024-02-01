package com.pmdm.practica3.ui.cerrar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.pmdm.practica3.viewmodel.login.LoginActivity;

public class CerrarFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "login_prefs";
    private static final String KEY_USER = "user";
    private static final String KEY_PASSWD = "passwd";
    private static final String KEY_REMEMBER = "remember";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        cerrarSesion();
    }

    /**
     * Método para cerrar sesión
     */
    public void cerrarSesion() {
        // Limpiar datos en sharedpreferences al cerrar sesión
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER);
        editor.remove(KEY_PASSWD);
        editor.remove(KEY_REMEMBER);
        editor.apply();
        Toast.makeText(getContext(),"Sesión cerrada",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
