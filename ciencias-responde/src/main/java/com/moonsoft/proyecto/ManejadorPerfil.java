/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Pregunta;
import com.moonsoft.proyecto.model.Tema;
import com.moonsoft.proyecto.model.Usuario;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.persistence.CascadeType;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.Query;

/**
 *
 * @author gouen
 */
@ManagedBean(name = "manejadorPerfil")
@ViewScoped
public class ManejadorPerfil {
    
    private Usuario usr;
    
    public Usuario getUsuario(String id) {
       if (id.equals("")) return null;
       if (usr == null) {
           try {
               ConexionBD.conectarBD();
               Query q = ConexionBD.consultarBD("Usuario.findByIdUsuario");
               q.setParameter("idUsuario", Integer.parseInt(id));
               usr = (Usuario) q.getSingleResult();
           }
           catch (NoResultException e) {
               return null;
           }
       }
       return usr;
    }
    
    public static boolean esAdmin() {
        FacesContext context = getCurrentInstance();
        Usuario usr = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return usr.getEsAdmin();
    }
    
    public String idActivo(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Usuario u = (Usuario) ec.getSessionMap().get("usuario");
        return u.getIdUsuario().toString();
    }
    
    public String veAPerfil(Integer id) {
        return "PerfilIH.xhtml?faces-redirect=true&uid="+id;
    }
    
    public String borrarUsuario(){
        usr.eliminarBD();
        return "PantallaPrincipalIH.xhtml?faces-redirect=true";
    }
}
