/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto.model;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.push.annotation.Singleton;

/**
 *
 * @author gouen
 */
@Singleton
public class ConexionBD {

    @PersistenceContext
    private static EntityManager em;
    
    private ConexionBD () {}
    
    public static EntityManager conectarBD(){
        if (em == null) {
            em = Persistence.createEntityManagerFactory("com.moonsoft.proyecto_ciencias-responde_war_0.0.1PU").createEntityManager();
        }
        return em;
    }
    
    public static void desconectarBD(){
        if (em != null) {
                em.close();
        }
    }
    
    public static void insertarBD(Object o){
        try{
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
            System.out.println("El usuario se agrego correctamente a la base");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void actualizarBD(){
    
    }
    
    public static void borrarBD(){
    
    }
    
    public static Query consultarBD(String q){
        if (em == null) return null;
        return em.createNamedQuery(q);
    }
    
    public static void seleccionarTodosBD(){
    
    }

}