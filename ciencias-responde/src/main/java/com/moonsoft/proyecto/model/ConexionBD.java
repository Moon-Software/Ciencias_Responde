/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.primefaces.push.annotation.Singleton;

/**
 *
 * @author Diego Jesus Favela Nava
 */
@Singleton
public class ConexionBD {

    @PersistenceContext
    private static EntityManager em;
    private static EntityManagerFactory emf;

    private ConexionBD() {
    }

    /**
     * Método que nos conecta a la base de datos.
     *
     * @return no
     */
    public static void conectarBD() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("APP1PU");
            }
        } catch (PersistenceException pe) {
        }
    }

    /**
     * Método que nos descomecta de la BD.
     *
     * @return no
     */
    public static void desconectarBD() {
        if (emf != null) {
            emf.close();
        }
    }

    /**
     * Método que inserta un objeto en la BD.
     *
     * @param o
     */
    public static void insertarBD(Object o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Método que borra un objeto de la base de datos.
     *
     * @return no
     */
    public static void borrarBD(Object o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (!em.contains(o)) {
            o = em.merge(o);
        }
        em.remove(o);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Método que guarda devuelve una consulta a partir de una cadena.
     *
     * @return consulta-
     */
    public static Query consultarBD(String q) {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery(q);
    }

}
