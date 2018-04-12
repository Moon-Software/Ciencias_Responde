/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.Comentario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
//import javax.persistence.Query;

/**
 *
 * @author Diego Jesus Favela Nava
 */

@ManagedBean(name = "manejadorComentario")
@ViewScoped
public class ManejadorComentario {
    
    /**
     * Creates a new instance of ManejadorComentario
     * @return lista de comentarios
     */
    public List<Comentario> getComentarios() {
        //Query q = em.createQuery("SELECT * From comentario");
        return null;
    }
    
}
