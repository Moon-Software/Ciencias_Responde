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

    public static void conectarBD() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("APP1PU");
            }
        } catch (PersistenceException pe) {}
    }

    public static void desconectarBD() {
        if (emf != null) {
            emf.close();
        }
    }

    public static void insertarBD(Object o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }

    public static void actualizarBD() {

    }
    
    public static void borrarBD(Object o){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (!em.contains(o)) {
            o = em.merge(o);
        }
        em.remove(o);
        em.getTransaction().commit();
        em.close();
    }

    public static Query consultarBD(String q) {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery(q);
    }

    public static void seleccionarTodosBD() {

    }

}
