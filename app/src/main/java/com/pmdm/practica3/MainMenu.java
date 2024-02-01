package com.pmdm.practica3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText etUser, etPassword;
    private Button btnEntrar;
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

        ivUser = headerView.findViewById(R.id.ivUser);
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);

        setSupportActionBar(binding.appBarMain2.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_medicion, R.id.nav_conversor, R.id.nav_configuracion, R.id.nav_cerrar)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setUserData(usuarioModel);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setUserData(UsuarioModel usuarioModel){
        String name = usuarioModel.getName();
        String email = usuarioModel.getEmail();
        Log.i("setUserData", name + " / " + email);
        ivUser.setImageResource(R.drawable.user_pic);
        tvName.setText(name);
        tvEmail.setText(email);
    }
}