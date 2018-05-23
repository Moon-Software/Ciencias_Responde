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

    public static EntityManager conectarBD() {
        try {
            if (em == null) {
                emf = Persistence.createEntityManagerFactory("APP1PU");
                em = emf.createEntityManager();
            }
        } catch (PersistenceException pe) {

        }
        return em;
    }

    public static void desconectarBD() {
        if (em != null) {
            em.close();
        }
    }

    public static void insertarBD(Object o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public static void actualizarBD() {

    }

    public static void borrarBD() {

    }

    public static Query consultarBD(String q) {
        if (em == null) {
            return null;
        }
        return em.createNamedQuery(q);
    }

    public static void seleccionarTodosBD() {

    }

}
