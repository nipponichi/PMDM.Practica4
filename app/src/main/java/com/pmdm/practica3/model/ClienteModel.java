package com.pmdm.practica3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ClienteModel implements Parcelable, Serializable {
    private String id;
    private String nombre;
    private String apellidos;
    private int temperatura;
    private int format;
    private String ciudad;
    private String provincia;

    /**
     * Constructor por defecto
     */
    public ClienteModel() {

    }

    /**
     * Contructor de Cliente
     *
     * @param id
     * @param nombre
     * @param apellidos
     * @param temperatura
     * @param format
     * @param ciudad
     * @param provincia
     */
    public ClienteModel(String id, String nombre, String apellidos, int temperatura, int format, String ciudad, String provincia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.temperatura = temperatura;
        this.format = format;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Selector de formato de grados
     *
     * @param celsius
     * @return
     */
    public int formatChooser(boolean celsius) {
        if (celsius) {
            return 1;
        } else {
            return 2;
        }
    }

    protected ClienteModel(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        temperatura = in.readInt();
        format = in.readInt();
        ciudad = in.readString();
        provincia = in.readString();
    }

    public static final Creator<ClienteModel> CREATOR = new Creator<ClienteModel>() {
        @Override
        public ClienteModel createFromParcel(Parcel in) {
            return new ClienteModel(in);
        }

        @Override
        public ClienteModel[] newArray(int size) {
            return new ClienteModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nombre);
        parcel.writeString(apellidos);
        parcel.writeInt(temperatura);
        parcel.writeInt(format);
        parcel.writeString(ciudad);
        parcel.writeString(provincia);
    }

}
