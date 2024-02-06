package com.pmdm.practica3.model;

import java.io.Serializable;

public class UsuarioModel implements Serializable {
    String name, passwd, email;

    /**
     * Constructor de Usuario
     */
    public UsuarioModel(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
        this.email = name + "@elcampico.org";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
