package com.pmdm.practica3.ui.localizacion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pmdm.practica3.R;

import com.pmdm.practica3.model.ClienteModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalizacionFragment extends Fragment implements OnMapReadyCallback, LocalizacionViewModel.LocalizacionViewModelCallback {

    private GoogleMap myMap;
    private ArrayList<ClienteModel> clienteModelList = new ArrayList<>();

    SupportMapFragment mapFragment = null;

    /**
     * Crea la vista del fragment Localizacion
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_localizacion, container, false);
        LocalizacionViewModel localizacionViewModel = new ViewModelProvider(this).get(LocalizacionViewModel.class);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        localizacionViewModel.doList(getContext());
        localizacionViewModel.setCallback(this);

        return root;
    }

    /**
     * Toma nombre y población de los clientes en el mapa.
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        String ciudad, nombre;
        Log.i("previo forEach", "creado");

        for (ClienteModel cliente : clienteModelList) {
            ciudad = cliente.getCiudad();
            nombre = (cliente.getNombre() + " " + cliente.getApellidos());
            Log.i("Ciudad ", ciudad);
            mapSignal(ciudad, nombre);
        }
    }

    /**
     * Obtiene la lista de clientes cargada
     *
     * @param clienteModelList
     */
    @Override
    public void onListResponse(ArrayList<ClienteModel> clienteModelList) {
        this.clienteModelList = clienteModelList;
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    /**
     * Si la lista de clientes no se recibe, mostramos un mensaje de error por consola
     *
     * @param error
     */
    @Override
    public void onError(String error) {
        Log.i("error", "error");
    }

    /**
     * Señala y etiqueta al cliente en el mapa
     *
     * @param ciudad
     * @param nombre
     */
    public void mapSignal(String ciudad, String nombre) {
        List<Address> addressList = null;

        if (ciudad != null) {
            Geocoder geocoder = new Geocoder(getContext());

            try {
                addressList = geocoder.getFromLocationName(ciudad, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Address address = addressList.get(0);
        LatLng coordenadasCiudad = new LatLng(address.getLatitude(), address.getLongitude());
        myMap.addMarker(new MarkerOptions().position(coordenadasCiudad).title(nombre));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(coordenadasCiudad));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadasCiudad, 4));
    }
}
