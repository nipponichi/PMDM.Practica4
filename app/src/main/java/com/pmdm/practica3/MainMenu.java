package com.pmdm.practica3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.pmdm.practica3.databinding.MenuMainBinding;
import com.pmdm.practica3.model.UsuarioModel;

public class MainMenu extends AppCompatActivity {

    private UsuarioModel usuarioModel;
    private AppBarConfiguration mAppBarConfiguration;
    private MenuMainBinding binding;

    ImageView ivUser;
    TextView tvName;
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);

        super.onCreate(savedInstanceState);

        binding = MenuMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        usuarioModel = (UsuarioModel) intent.getSerializableExtra("UsuarioModel");
        Log.i("Usuario en MainMenu: ", usuarioModel.getName() + ", " + usuarioModel.getEmail());

        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);

        // Campos de usuario de navigation drawer
        ivUser = headerView.findViewById(R.id.ivUser);
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);

        setSupportActionBar(binding.appBarMain2.toolbar);

        // Establece el orden del menú
        DrawerLayout drawer = binding.drawerLayout;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_medicion, R.id.nav_conversor, R.id.nav_configuracion, R.id.nav_localizacion, R.id.nav_cerrar)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Establece los datos de usuario
        setUserData(usuarioModel);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Modifica los datos del usuario en el navigation drawer
     *
     * @param usuarioModel
     */
    public void setUserData(UsuarioModel usuarioModel) {
        String name = usuarioModel.getName();
        String email = usuarioModel.getEmail();
        Log.i("setUserData", name + " / " + email);
        tvName.setText(name);
        tvEmail.setText(email);
    }
}