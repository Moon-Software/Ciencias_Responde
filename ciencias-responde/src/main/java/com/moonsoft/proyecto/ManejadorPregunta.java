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

    /**
     * Setter de alguna búsqueda relacionada a una pregunta.
     *
     * @param busqueda
     */
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    /**
     * Regresa la búsqueda.
     *
     * @return busqueda
     */
    public String getBusqueda() {
        return this.busqueda;
    }

    /**
     * Método que regresa la lista de todos los temas disponibles
     *
     * @return list_of_temas
     */
    public List<Tema> getTemas() {
        try {
            if (temas == null) {
                ConexionBD.conectarBD();
                Query q = ConexionBD.consultarBD("Tema.findAll");
                temas = (List<Tema>) q.getResultList();
            }
            return temas;
        } catch (Exception n) {
            return null;
        }
    }

    /**
     * Método que agrega una pregunta a la BD y nos redirecciona a ésta.
     *
     * @return enlace
     */
    public String agregarPregunta() {
        try {
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
            return "PantallaPreguntaIH.xhtml?faces-redirect=true&amp;pid=" + pregunta.getIdPregunta();

        } catch (Exception n) {
            return "ErrorConexionIHF.xhtml?faces-redirect=true";
        }
    }

    /**
     * Revisa si una pregunta es de un usuario o administrador (los admins
     * tienen más permisos para borrar).
     *
     * @return boolean.
     */
    public boolean esBorrable() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Usuario u = (Usuario) ec.getSessionMap().get("usuario");
        if (u == null) {
            return false;
        }
        if (u.getEsAdmin()) {
            return true;
        }
        return pregunta.getIdUsuario().getIdUsuario().equals(u.getIdUsuario());
    }

    /**
     * Borra una pregunta de la base de datos y nos redirecciona a la pantalla
     * de inicio.
     *
     */
    public String borrarPregunta() {
        try {
            if (pregunta != null) {
                ConexionBD.borrarBD(pregunta);
            }
            return "PantallaPrincipalIH.xhtml?faces-redirect=true";
        } catch (Exception n) {
            return "ErrorConexionIHF.xhtml?faces-redirect=true";
        }
    }

    /**
     * Revisa si una palabra está contenida en un arreglo de strings.
     *
     * @param a
     * @param ele
     * @return
     */
    public static boolean estaContenida(String[] a, String ele) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == ele || a[i].equals(ele)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Nos redirecciona a una pregunta a partir de un id.
     *
     * @param id
     * @return enlace
     */
    public String veAPregunta(Integer id) {
        return "PantallaPreguntaIH.xhtml?faces-redirect=true&pid=" + id;
    }

    /**
     * Nos regresa el id de ésta pregunta, si es null, regresa 8.
     *
     * @return id
     */
    public String getId() {
        if (pregunta == null) {
            return "8";
        }
        return pregunta.getIdPregunta().toString();
    }

    /**
     * Obtiene una pregunta a partir de un id.
     *
     * @param id
     * @return pregunta
     */
    public Pregunta getPregunta(String id) {
        try {
            if (pregunta != null) {
                return pregunta;
            } else {
                if (id.equals("")) {
                    return null;
                }
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

        } catch (Exception n) {
            return null;
        }
    }

    /**
     * Regresa una lista de preguntas a partir de un tema dado.
     *
     * @param tema
     * @return list_of_preguntas
     */
    public List<Pregunta> getPreguntas(String tema) {
        try {
            List<Pregunta> preguntas;
            ConexionBD.conectarBD();
            Query q1 = ConexionBD.consultarBD("Pregunta.findByTema");
            Query q2 = ConexionBD.consultarBD("Tema.findByNombre");
            q2.setParameter("nombre", tema);
            Tema t = (Tema) q2.getSingleResult();
            q1.setParameter("tema", t);
            preguntas = (List<Pregunta>) q1.getResultList();

            return preguntas;
        } catch (Exception n) {
            return null;
        }
    }

    /**
     * Dada una cadena, quita acentos de ésta.
     *
     * @param str
     * @return str_sin_acentos
     */
    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    /**
     * Método que regresa un enlace de para la pantalla de búsqueda.
     *
     * @return enlace
     */
    public String buscaPreguntasPorPalabras() {
        return "PreguntasFiltradasIH.xhtml?faces-redirect=true&search=" + this.busqueda;
    }

    /**
     * Método que regresa una lista de preguntas tal que éstas estén
     * relacionadas con una búsqueda específica.
     *
     * @param busqueda
     * @return list_of_preguntas.
     */
    public List<Pregunta> getPreguntasPorPalabras(String busqueda) {
        try {
            busqueda = deAccent(busqueda.toLowerCase());

            String[] tmp = busqueda.split(" |\\+");
            LinkedList<String> palabras = new LinkedList<String>();
            LinkedList<String> palabrasFinal = new LinkedList<String>();
            String[] nexpr = {"la", "el", "los", "las", "y", "tan",
                "pero", "que", "por", "cual", "donde",
                "es", "en", "un", "unos", "son",
                "porque", "a", "e", "u", "i", "o"};

            for (String tmp1 : tmp) {
                palabras.add(tmp1.replaceAll("[^\\dA-Za-z]", ""));
            }

            for (String p : palabras) {
                if (!p.equals(nexpr) && p.length() > 3) {
                    palabrasFinal.add(p);
                }
            }

            List<Pregunta> resultado = new LinkedList<Pregunta>();
            for (Tema t : getTemas()) {
                for (Pregunta p : getPreguntas(t.getNombre())) {
                    for (String s : palabrasFinal) {
                        if (contienePalabras(s, p.getTitulo().split(" |\\+"))) {
                            resultado.add(p);
                        }
                    }
                }
            }

            return resultado;

        } catch (Exception n) {
            return null;
        }

    }

    /**
     * Método que verifica que un string esté al menos una vez en un arreglo de
     * palabras.
     *
     * @param pregunta
     * @param palabras
     * @return
     */
    public boolean contienePalabras(String str, String[] palabras) {
        int contador = 0;

        for (String p : palabras) {
            if (str.equals(deAccent(p.toLowerCase()).replaceAll("[^\\dA-Za-z]", ""))) {
                contador++;
            }
        }

        return contador > 0;
    }
}
