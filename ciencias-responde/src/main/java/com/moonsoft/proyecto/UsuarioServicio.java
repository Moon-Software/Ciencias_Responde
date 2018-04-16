/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Usuario;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Lob;
import org.primefaces.model.UploadedFile;

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
    @Lob
    private UploadedFile file;
 
    public UploadedFile getFile() {
       return file;
    }

    public void setFile(UploadedFile file) {
       this.file = file;
    }
    
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
        if(contrasenia.length() >= 8){
            if(mather.find() == true){
                System.out.println(nombre);
                System.out.println(correo);
                System.out.println(contrasenia);
                Usuario usr = new Usuario(0,correo,nombre,null,contrasenia,new Date(),false,"activo");
                Email em = new Email(correo,nombre,contrasenia);
                em.sendEmail();
                ConexionBD.conectarBD();
                usr.guardarBD();
                respuesta = "registroConfirmacionIH.xhtml?faces-redirect=true";
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El correo debe tener dominio @ciencias"));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La contraseña de contener al menos 8 caracteres"));
        }
        return respuesta;   
    }
}
