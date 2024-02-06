package com.pmdm.practica3.ui.cerrar;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.pmdm.practica3.viewmodel.login.LoginActivity;

public class CerrarFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cerrarSesion();
    }

    /**
     * Método para cerrar sesión
     */
    public void cerrarSesion() {
        Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
