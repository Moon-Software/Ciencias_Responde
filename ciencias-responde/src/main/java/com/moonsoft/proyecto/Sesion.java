/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.persistence.Query;

/**
 *
 * @author Faded
 */
@ManagedBean(name = "sesion")
@SessionScoped
public class Sesion {

    private Usuario usuario;

    /**
     * Creates a new instance of Sesion
     */
    public Sesion() {
        usuario = new Usuario();
    }

    /**
     *
     * @return
     */
    public Usuario getusuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public String iniciarSesion() {
        try{
        Usuario l = encuentraUsuario(usuario.getCorreo(), usuario.getContrasenia());
        boolean logged = l != null;
        if (logged) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", l);
            return "PantallaPrincipalIH.xhtml?faces-redirect=true";
        }

        return "InicioSesionIH.xhtml?faces-redirect=true";
        }catch(Exception n){
            return "ErrorConexionIHF.xhtml?faces-redirect=true";
        }
    }

    /**
     *
     * @return
     */
    public String cerrarSesion() {
        try{
        System.out.println("chingados");
        FacesContext context = getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "PantallaPrincipalIH.xhtml?faces-redirect=true";
        }catch(Exception n){
            return "ErrorConexionIHF.xhtml?faces-redirect=true";
        }
    }

    /**
     *
     * @return
     */
    public boolean estaConectado() {
        FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return l != null;
        
    }

    /**
     *
     * @return
     */
    public static Usuario getUsuario() {
        FacesContext context = getCurrentInstance();
        return (Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    /**
     *
     * @param correo
     * @param contrasenia
     * @return
     */
    public Usuario encuentraUsuario(String correo, String contrasenia) {
        ConexionBD.conectarBD();
        Query q = ConexionBD.consultarBD("Usuario.findByCorrAndContra")
                .setParameter("correo", correo)
                .setParameter("contrasenia", contrasenia);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Usuario) q.getSingleResult();
    }
}
