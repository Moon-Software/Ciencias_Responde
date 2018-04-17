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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.persistence.Query;


/**
 *
 * @author Diego Jesus Favela Nava
 */

@ManagedBean(name = "manejadorComentario")
@ViewScoped
public class ManejadorComentario {
    
    private List<Comentario> comentarios;
    
    
    public void agregarComentario(Pregunta p) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Usuario u = (Usuario) ec.getSessionMap().get("usuario");
        String contenido = ec.getRequestParameterMap().get("form:contenido");

        //Agregamos pregunta, junto con su tema a la base de datos, 
        Comentario c = new Comentario(0, contenido, 0, new Date());
        System.out.println(c.getFecha());
        c.setIdPregunta(p);
        c.setIdUsuario(u);
        //pregunta.setUsuario(usr);
        c.guardarBD();
    }
    
    /**
     * Creates a new instance of ManejadorComentario
     * @param id nada
     * @return lista de comentarios
     */
    public List<Comentario> getComentarios(Pregunta p) {
        if (comentarios == null) {
            ConexionBD.conectarBD();
            Query q = ConexionBD.consultarBD("Comentario.findByIdPregunta");
            q.setParameter("idPregunta", p);
            comentarios = (List<Comentario>) q.getResultList();
        }
        return comentarios;
    }
    
    public boolean hayComentarios(Pregunta p) {
        getComentarios(p);
        return !comentarios.isEmpty();
    }
    
}
