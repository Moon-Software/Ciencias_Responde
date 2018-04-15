/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.Comentario;
import com.moonsoft.proyecto.model.Pregunta;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Diego Jesus Favela Nava
 */
@ManagedBean(name = "comentariosIH")
@ViewScoped
public class ComentariosIH {

    private ManejadorComentario mc = new ManejadorComentario();
    
    public String comentarioNuevo(Pregunta p) {
        //Usuario u = getusuario.
        //if (u == null)  No esta conectado
        //  return ""
        //else
        return "";  //Agregar comentario nuevo.
    }
    
    public String getComentarios(Pregunta p) {
        List<Comentario> comentarios = mc.getComentarios(p);
        if (comentarios.isEmpty()) {
            return "No hay comentarios para esta pregunta.";
        }
        //Repetir muchas veces.
        return  "<ui:repeat value=\"#{manejadorComentario.comentarios}\" var=\"com\" >\n" +
                    "<div id=\"comentario\">\n" +
                        "<h:outputText value=\"#{com.contenido}\" />\n" +
                    "</div>\n" +
                "</ui:repeat>";
    }
}
