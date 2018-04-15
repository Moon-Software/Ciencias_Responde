/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto.web;

import com.moonsoft.proyecto.model.EntityProvider;
import com.moonsoft.proyecto.model.Login;
import com.moonsoft.proyecto.model.LoginJpaController;
import com.moonsoft.proyecto.model.Usuario;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author miguel
 */
@ManagedBean
@SessionScoped
public class LoginController {

    private EntityManagerFactory emf;
    private LoginJpaController jpaController;
    private Usuario usuario;

    public LoginController() {
        System.out.println("puto11");
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new LoginJpaController(emf);
        usuario = new Usuario();
    }

    public Usuario getusuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String canLogin() {
        Login l = jpaController.findLogin(usuario.getUsuario(), usuario.getContraseña());
        boolean logged = l != null;
        if (logged) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", l);
                
            System.out.println("jhgklhjkg");
            return "secured/acceso?faces-redirect=true";
        }

        return "index?faces-redirect=true";
    }

    public String logout() {
        System.out.println("chingados");
        FacesContext context = getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

}
