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
import java.text.Normalizer;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
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
    private String busqueda;

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getBusqueda() {
        return this.busqueda;
    }

    public List<Tema> getTemas() {
        if (temas == null) {
            ConexionBD.conectarBD();
            Query q = ConexionBD.consultarBD("Tema.findAll");
            temas = (List<Tema>) q.getResultList();
        }
        return temas;
    }

    public static boolean estaContenida(String[] a, String ele) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == ele || a[i].equals(ele)) {
                return true;
            }
        }
        return false;
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
        pregunta = new Pregunta(0, descripcion, titulo, new Date());
        for (Tema t : temas) {
            if (t.getNombre().equals(tNom)) {
                pregunta.setTema(t);
            }
        }
        Usuario u = (Usuario) ec.getSessionMap().get("usuario");
        pregunta.setIdUsuario(u);
        pregunta.guardarBD();
        return "PantallaPreguntaIH.xhtml?faces-redirect=true&pid=" + pregunta.getIdPregunta();
    }

    public String veAPregunta(Integer id) {
        return "PantallaPreguntaIH.xhtml?faces-redirect=true&pid=" + id;
    }

    public String getId() {
        if (pregunta == null) {
            return "8";
        }
        return pregunta.getIdPregunta().toString();
    }

    public Pregunta getPregunta(String id) {
        if (id.equals("")) {
            return null;
        }
        if (pregunta == null) {
            try {
                ConexionBD.conectarBD();
                Query q = ConexionBD.consultarBD("Pregunta.findByIdPregunta");
                q.setParameter("idPregunta", Integer.parseInt(id));
                pregunta = (Pregunta) q.getSingleResult();
            } catch (NoResultException e) {
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

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public String buscaPreguntasPorPalabras() {
        return "PreguntasFiltradasIH.xhtml?faces-redirect=true&search=" + this.busqueda;
    }

    public List<Pregunta> getPreguntasPorPalabras(String busqueda) {
        busqueda = busqueda.toLowerCase();
        busqueda = deAccent(busqueda);

        String[] tmp = busqueda.split(" |\\+");
        LinkedList<String> palabras = new LinkedList<String>();
        LinkedList<String> palabrasFinal = new LinkedList<String>();
        String[] nexpr = {"la", "el", "los", "las", "y", "tan", "pero", "que", "por", "cual", "donde", "es", "en", "un", "unos", "son", "porque"};

        for (int i = 0; i < tmp.length; i++) {
            palabras.add(tmp[i].replaceAll("[^\\dA-Za-z]", ""));
        }

        for (String palabra : palabras) {
            if (!estaContenida(nexpr, palabra)) {
                palabrasFinal.add(palabra);
            }
        }

        String[] temass = {"Horarios", "Inscripciones", "Servicio Social", "Titulación", "Posgrado", "Becas"};
        List<Pregunta> preguntas = new LinkedList<>();
        List<Pregunta> resultado = new LinkedList<>();

        for (String tema : temass) {
            preguntas.addAll(getPreguntas(tema));
        }

        for (Pregunta preguntap : preguntas) {
            if (contienePalabras(preguntap, palabrasFinal)) {
                resultado.add(preguntap);
            }
        }

        return resultado;
    }

    public boolean contienePalabras(Pregunta pregunta, LinkedList<String> palabras) {
        int contador = 0;

        for (String palabra : palabras) {
            if (deAccent(pregunta.getDescripcion().toLowerCase()).replaceAll("[^\\dA-Za-z]", " ").contains(palabra)
                    || deAccent(pregunta.getTitulo().toLowerCase()).replaceAll("[^\\dA-Za-z]", " ").contains(palabra)) {
                contador++;
            }
        }

        return contador > 0;
    }
}
