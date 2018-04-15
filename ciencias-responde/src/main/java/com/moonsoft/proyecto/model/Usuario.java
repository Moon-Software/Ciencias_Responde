package com.moonsoft.proyecto.model;

public class Usuario {

    private String usuario;
    private String contraseña;
    private String confirmacionContraseña;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getConfirmacionContraseña() {
        return confirmacionContraseña;
    }

    public void setConfirmacionContraseña(String confirmacionContraseña) {
        this.confirmacionContraseña = confirmacionContraseña;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }
    
    public void guardarBD() {
        ConexionBD.conectarBD();
        ConexionBD.insertarBD(this); 
    }

}
