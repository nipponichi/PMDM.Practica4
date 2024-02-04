package com.pmdm.practica3.ui.localizacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pmdm.practica3.MainMenu;
import com.pmdm.practica3.R;
import com.pmdm.practica3.model.UsuarioModel;

import java.io.IOException;
import java.util.List;

public class LocalizacionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myClientMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localizacion_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        List <Address>  addressList = null;
        Intent intent = getIntent();
        String ciudad = (String) intent.getSerializableExtra("ciudad");

        if (ciudad != null) {
            Geocoder geocoder = new Geocoder(LocalizacionActivity.this);

            try {
                addressList = geocoder.getFromLocationName(ciudad,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Address address = addressList.get(0);

        myClientMap = googleMap;
        LatLng ciudadCoordenadas = new LatLng(address.getLatitude(),address.getLongitude());
        myClientMap.addMarker(new MarkerOptions().position(ciudadCoordenadas).title(ciudad));
        myClientMap.moveCamera(CameraUpdateFactory.newLatLng(ciudadCoordenadas));
        myClientMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ciudadCoordenadas,10));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            UsuarioModel usuarioModel = new UsuarioModel();
            Intent intent = new Intent(this, MainMenu.class);
            intent.putExtra("UsuarioModel", usuarioModel);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

