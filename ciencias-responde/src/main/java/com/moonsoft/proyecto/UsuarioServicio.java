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
import javax.persistence.Query;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author gouen
 */
@ManagedBean(name = "usuarioServicio")
@ViewScoped
public class UsuarioServicio {

    private String correo;
    private String nombre;
    private String contrasenia;
    @Lob
    private UploadedFile file;

    /**
     *
     * @return
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     *
     * @param file
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     *
     * @return
     */
    public String getCorreo() {
        return correo;
    }

    /**
     *
     * @param correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     *
     * @param contrasenia
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     *
     * @return
     */
    public String agregarUsuario() {

        String respuesta = "";
        Pattern correoVal = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "ciencias\\.unam\\.mx$");
        Matcher mather = correoVal.matcher(correo);
        if (contrasenia.length() >= 8) {
            if (true == mather.find()) {
                ConexionBD.conectarBD();
                Query q = ConexionBD.consultarBD("Usuario.findByCorreo");
                q.setParameter("correo", correo);
                if (!q.getResultList().isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El usuario ya existe."));
                    return "";
                }
                Usuario usr = new Usuario(0, correo, nombre, contrasenia, new Date(), false);
                Email em = new Email(correo, nombre, contrasenia);
                em.sendEmail();
                usr.guardarBD();
                respuesta = "RegistroExitosoIH.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El correo debe tener dominio @ciencias.unam.mx"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La contraseña de contener al menos 8 caracteres"));
        }
        return respuesta;
    }
}
