package com.pmdm.practica3.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.practica3.databinding.FragmentInicioBinding;
import com.pmdm.practica3.model.ClienteModel;
import com.pmdm.practica3.model.ClienteAdapter;

import java.util.ArrayList;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding binding;
    private ArrayList<ClienteModel> clienteModelList = new ArrayList<>();
    private ClienteAdapter mAdapter;


    /**
     * Creación de la vista del fragment
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new ClienteAdapter(new ArrayList<>(), getContext());
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);
        inicioViewModel.setAdapter(mAdapter);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtiene el listado de clientes
        clienteModelList = inicioViewModel.doList(getContext());

        // Inicia el recyclerView
        RecyclerView recyclerView = binding.rvCliente;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Añadimos el adaptador al recyclerView
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    /**
     * Libera recursos durante el cierre del fragment
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mAdapter = null;
    }

}
