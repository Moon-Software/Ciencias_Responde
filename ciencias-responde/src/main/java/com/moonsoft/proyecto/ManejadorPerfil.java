/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author gouen
 */
@ManagedBean(name = "manejadorPerfil")
@ViewScoped
public class ManejadorPerfil {

    private Usuario usr;
    
    /**
     * Método que obtiene un usuario a parti de un id.
     *
     * @param id
     * @return usuario
     */
    public Usuario getUsuario(String id) {
        try {
            if (id.equals("")) {
                return null;
            }
            if (usr == null) {
                try {
                    ConexionBD.conectarBD();
                    Query q = ConexionBD.consultarBD("Usuario.findByIdUsuario");
                    q.setParameter("idUsuario", Integer.parseInt(id));
                    usr = (Usuario) q.getSingleResult();
                } catch (NoResultException e) {
                    return null;
                }
            }
            return usr;
        } catch (Exception n) {
            return null;
        }
    }
    
    /**
     * Método que revisa si éste es el perfil de algún administrador.
     * 
     * @return boolean
     */
    public static boolean esAdmin() {
        try {
            FacesContext context = getCurrentInstance();
            Usuario usr = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
            return usr.getEsAdmin();
        } catch (Exception n) {
            return false;
        }
    }
    
    /**
     * Método que regresa el id del usuario que se encuentra activo.
     * 
     * @return id
     */
    public String idActivo() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            Usuario u = (Usuario) ec.getSessionMap().get("usuario");
            return u.getIdUsuario().toString();
        } catch (Exception n) {
            return null;
        }
    }
    
    /**
     * Método que se redirige a un perfil a partir de un id.
     *
     * @param id
     * @return enlace
     */
    public String veAPerfil(Integer id) {
        return "PerfilIH.xhtml?faces-redirect=true&uid=" + id;
    }
    
    /**
     * Método que se encarga de borrar a un usuario de la base de datos.
     * 
     * @return enlace
     */
    public String borrarUsuario2() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Usuario us = (Usuario) ec.getSessionMap().get("usuario");
        try{
            if(usr.getIdUsuario() == us.getIdUsuario()){
                Sesion.cerrarSesion();
                usr.eliminarBD();
            }else{
                usr.eliminarBD();
            }
            return "PantallaPrincipalIH.xhtml?faces-redirect=true";
        }catch(Exception n){
            return "ErrorConexionIH.xhtml?faces-redirect=true";
        }
    }
    
    public boolean esBorrable(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Usuario us = (Usuario) ec.getSessionMap().get("usuario");
        if(us == null){
            return false;
        }
        if(us.getEsAdmin()){
            return true;
        }
        return usr.getIdUsuario().equals(us.getIdUsuario());
    }
    
    /**
     * Metodo que se encarga de convertir algo de tipo Date a String
     * @return fecha
     */
    public String dateToString(){
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        return fecha.format(usr.getFRegistro());
    }
}
