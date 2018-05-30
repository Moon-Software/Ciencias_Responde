/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.Comentario;
import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Pregunta;
import com.moonsoft.proyecto.model.Usuario;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Diego Jesus Favela Nava
 */
@ManagedBean(name = "manejadorComentario")
@ViewScoped
public class ManejadorComentario {

    private List<Comentario> comentarios;

    /**
     * Método que se encarga de agregar un comentario a la base de datos.
     *
     * @param p
     * @return no
     */
    public String agregarComentario(Pregunta p) {
        try {
            if (p == null) {
                return "PantallaPrincipalIH.xhtml";
            }
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            Usuario u = (Usuario) ec.getSessionMap().get("usuario");
            String contenido = ec.getRequestParameterMap().get("areacom:contenido");
            
            
            //Agregamos pregunta, junto con su tema a la base de datos, 
            Comentario c = new Comentario(0, contenido, 0, new Date());
            c.setIdPregunta(p);
            c.setIdUsuario(u);
            //pregunta.setUsuario(usr);
            ConexionBD.insertarBD(c);
            return "PantallaPreguntaIH.xhtml?faces-redirect=true&amp;pid=" + p.getIdPregunta();
        } catch (Exception n) {
            return "ErrorConexionIH.xhtml?faces-redirect=true";
        }
    }

    /**
     * Regresa una lista de comentarios a partir de una pregunta.
     *
     * @param p sdfsdf
     * @param id nada
     * @return lista de comentarios
     */
    public List<Comentario> getComentarios(Pregunta p) {
        try {
            if (comentarios == null) {
                ConexionBD.conectarBD();
                Query q = ConexionBD.consultarBD("Comentario.findByIdPregunta");
                q.setParameter("idPregunta", p);
                comentarios = (List<Comentario>) q.getResultList();
            }
            return comentarios;
        } catch (Exception n) {
            return null;
        }
    }

    /**
     * Método que revisa si una pregunta tiene comentarios.
     *
     * @param p
     * @return si
     */
    public boolean hayComentarios(Pregunta p) {
        try{
        getComentarios(p);
        return !comentarios.isEmpty();
        }catch(Exception n){
            return false;
        }
    }
    
    /**
     * Método que revisa si un comentario es borrable para un actor.
     *
     * @param c comentario
     * @return boolean
     */
    public boolean esBorrable(Comentario c) {
        ExternalContext ec = getCurrentInstance().getExternalContext();
        Usuario u = (Usuario) ec.getSessionMap().get("usuario");
        if (u == null) {
            return false;
        }
        if (u.getEsAdmin()) {
            return true;
        }
        return c.getIdUsuario().getIdUsuario().equals(u.getIdUsuario());
    }
    
    /**
     * Método que borra un comentario de la BD.
     *
     * @param c comentario
     * @return boolean
     */
    public String borrarComentario(Comentario c) {
        try {
            String pid = c.getIdPregunta().getIdPregunta().toString();
            if (c != null) {
                Query q = ConexionBD.consultarBD("Comentario.findByIdComentario");
                q.setParameter("idComentario", c.getIdComentario());
                try {
                    c = (Comentario) q.getSingleResult();
                } catch (NoResultException e) {
                    FacesContext.getCurrentInstance().addMessage("avisos:aviso", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! El comentario ya fue eliminado.", "" ));
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                    return "PantallaPreguntaIH.xhtml?faces-redirect=true&amp;pid=" + pid;
                }

                ConexionBD.borrarBD(c);
            }else{
                return "ErrorConexionIH.xhtml?faces-redirect=true";
            }
            
            FacesContext.getCurrentInstance().addMessage("avisos:aviso", new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito! Se eliminó el comentario", "" ));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "PantallaPreguntaIH.xhtml?faces-redirect=true&amp;pid=" + pid;
        } catch (Exception n) {
            return "ErrorConexionIH.xhtml?faces-redirect=true";
        }
    }
}
