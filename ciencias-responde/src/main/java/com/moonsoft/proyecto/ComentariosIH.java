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
import javax.annotation.PostConstruct;


/**
 *
 * @author Diego Jesus Favela Nava
 */
@ManagedBean(name = "comentariosIH")
@ViewScoped
public class ComentariosIH {

    private List<Comentario> comentarios;
    private ManejadorComentario mc;
    
    @PostConstruct
    public void init() {    //deberan estar ordenados por fecha
        mc = new ManejadorComentario();
        
    }
    
    public List<Comentario> getComentarios() {
        return comentarios;
    }
}
