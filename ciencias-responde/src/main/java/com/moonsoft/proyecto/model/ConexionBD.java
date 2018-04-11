/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto.model;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gouen
 */
public class ConexionBD {
    @PersistenceContext
    private EntityManager em;
    
    public void conectarBD(){
        em = Persistence.createEntityManagerFactory("com.moonsoft.proyecto_ciencias-responde_war_0.0.1PU").createEntityManager();
    }
    
    public void insertarBD(Object o){
        em.getTransaction().begin();
    }
}