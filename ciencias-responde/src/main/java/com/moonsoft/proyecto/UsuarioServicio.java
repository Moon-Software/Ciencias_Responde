/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.Usuario;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author gouen
 */
@ManagedBean(name = "usuarioServicio" )
@ViewScoped
public class UsuarioServicio {
   private String correo;
   private String nombre;
   private String contrasenia;
    
   public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

   public String agregarUsuario(){
        String respuesta = "";
        Pattern correoVal = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "ciencias\\.unam\\.mx$");
        Matcher mather = correoVal.matcher(correo);
        if(mather.find() == true){
            System.out.println(nombre);
            System.out.println(correo);
            System.out.println(contrasenia);
            Usuario usr = new Usuario(0,correo,nombre,null,contrasenia,new Date(),false);
            Email em = new Email(correo);
            em.sendEmail();
            usr.guardarBD();
            respuesta = "registroConfirmacionIH.xhtml?faces-redirect=true";
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El correo debe tener dominio @ciencias"));
        }
        return respuesta;   
    }
}
