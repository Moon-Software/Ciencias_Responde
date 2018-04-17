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
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Diego Jesus Favela Nava
 */
@ManagedBean(name = "manejadorPregunta")
@ViewScoped
public class ManejadorPregunta {

    private Pregunta pregunta;
    private List<Tema> temas;
    
    
    public List<Tema> getTemas() {
        if (temas == null) {
            ConexionBD.conectarBD();
            Query q = ConexionBD.consultarBD("Tema.findAll");
            temas = (List<Tema>) q.getResultList();
        }
        return temas;
    }
    
    public String agregarPregunta() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String titulo = ec.getRequestParameterMap().get("form:titulo");
        String descripcion = ec.getRequestParameterMap().get("form:descripcion");
        String tNom = ec.getRequestParameterMap().get("form:tema");
        
        if (descripcion.equals("") || titulo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Se deben llenar ambos campos."));
            return "";
        }

        //Agregamos pregunta, junto con su tema a la base de datos, 
        pregunta = new Pregunta(0,descripcion, titulo, new Date());
        for (Tema t : temas) 
            if (t.getNombre().equals(tNom)) {
                pregunta.setTema(t);
            }
        Usuario u = (Usuario) ec.getSessionMap().get("usuario");
        pregunta.setIdUsuario(u);
        pregunta.guardarBD();
        return "PantallaPreguntaIH.xhtml?faces-redirect=true&pid="+pregunta.getIdPregunta();
    }
    
    public String veAPregunta(Integer id) {
        return "PantallaPreguntaIH.xhtml?faces-redirect=true&pid="+id;
    }
    
    public String getId() {
        if (pregunta == null) return "8";
        return pregunta.getIdPregunta().toString();
    }
    
    public Pregunta getPregunta(String id) {
        if (id.equals("")) return null;
        if (pregunta == null) {
            try {
                ConexionBD.conectarBD();
                Query q = ConexionBD.consultarBD("Pregunta.findByIdPregunta");
                q.setParameter("idPregunta", Integer.parseInt(id));
                pregunta = (Pregunta) q.getSingleResult();
            }
            catch (NoResultException e) {
                return null;
            }
        }
        return pregunta;
    }
    
    public List<Pregunta> getPreguntas(String tema) {
        List<Pregunta> preguntas;
        ConexionBD.conectarBD();
        Query q1 = ConexionBD.consultarBD("Pregunta.findByTema");
        Query q2 = ConexionBD.consultarBD("Tema.findByNombre");
        q2.setParameter("nombre", tema);
        Tema t = (Tema) q2.getSingleResult();
        q1.setParameter("tema", t);
        preguntas = (List<Pregunta>) q1.getResultList();
        
        return preguntas;
    }
    
}
