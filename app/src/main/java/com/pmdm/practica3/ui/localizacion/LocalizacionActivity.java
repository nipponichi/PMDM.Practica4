package com.pmdm.practica3.ui.localizacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

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
import com.pmdm.practica3.sharedPreferences.SharedConstants;

import java.io.IOException;
import java.util.List;

public class LocalizacionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myClientMap;
    private String usuario, passwd;

    /**
     * Configura la vista y el mapa.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localizacion_activity);

        // Habilita boton de retroceso y titulo para action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.menu_localizacion);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    /**
     * Muestra la localización de la ciudad en el mapa.
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        List<Address> addressList = null;
        Intent intent = getIntent();
        String ciudad = (String) intent.getSerializableExtra("ciudad");
        String nombre = (String) intent.getSerializableExtra("nombre");

        if (ciudad != null) {
            Geocoder geocoder = new Geocoder(LocalizacionActivity.this);

            try {
                addressList = geocoder.getFromLocationName(ciudad, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Address address = addressList.get(0);

        myClientMap = googleMap;
        LatLng ciudadCoordenadas = new LatLng(address.getLatitude(), address.getLongitude());
        myClientMap.addMarker(new MarkerOptions().position(ciudadCoordenadas).title(nombre));
        myClientMap.moveCamera(CameraUpdateFactory.newLatLng(ciudadCoordenadas));
        myClientMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ciudadCoordenadas, 10));
    }

    /**
     * Al clickar la flecha del action bar volvemos al menú principal.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            UsuarioModel usuarioModel;
            Intent intent = new Intent(this, MainMenu.class);
            usuarioModel = checkParametros();
            intent.putExtra("UsuarioModel", usuarioModel);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Añade los valores del usuario
     *
     * @return
     */
    public UsuarioModel checkParametros() {
        UsuarioModel usuarioModel;
        SharedPreferences shared = this.getSharedPreferences(SharedConstants.PREF_NAME, Context.MODE_PRIVATE);
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
}

