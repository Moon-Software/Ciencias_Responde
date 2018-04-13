/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Pregunta;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

/**
 *
 * @author Diego Jesus Favela Nava
 */
@ManagedBean(name = "manejadorPregunta")
@ViewScoped
public class ManejadorPregunta {

    private Pregunta pregunta;
    
    public String agregarPregunta() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String titulo = ec.getRequestParameterMap().get("form:titulo");
        String descripcion = ec.getRequestParameterMap().get("form:descripcion");
        pregunta = new Pregunta(0,descripcion, titulo, "ALGO", new Date());
        ConexionBD.conectarBD();
        ConexionBD.insertarBD(pregunta);
        return "PantallaPreguntaIH.xhtml?faces-redirect=true&pid="+pregunta.getIdPregunta();
    }
    
    public String veAPregunta(Integer id) {
        return "PantallaPreguntaIH?faces-redirect = true"+id;
    }
    
    public String getId() {
        if (pregunta == null) return "8";
        return pregunta.getIdPregunta().toString();
    }
    
    public Pregunta getPregunta(String id) {
        if (pregunta == null) {
            ConexionBD.conectarBD();
            Query q = ConexionBD.consultarBD("Pregunta.findByIdPregunta");
            q.setParameter("idPregunta", Integer.parseInt(id));
            pregunta = (Pregunta) q.getSingleResult();
        }
        return pregunta;
    }
    
}
