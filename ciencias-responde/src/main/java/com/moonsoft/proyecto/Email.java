package com.moonsoft.proyecto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 *
 * @author gouen
 */
public class Email {

    private final UsuarioServicio usr;
    final String fromEmail;
    final String password; 
    final String toEmail;
    private final String nombre;
    private final String contrasenia;

    /**
     * Contructor del mail
     * 
     * @param correo correo
     * @param nombre nombre
     * @param contrasenia mail
     */
    public Email(String correo, String nombre, String contrasenia) {
        this.usr = new UsuarioServicio();
        this.password = "ciencias123";
        this.fromEmail = "cienciasrespondeing@gmail.com";
        this.toEmail = correo;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    /**
     * Método para enviar correo.
     */
    public void sendEmail() {
        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");

        EmailUtil.sendEmail(session, toEmail, "Creacion de cuenta", 
                "Gracias por registrarte en CienciasResponde\n"
                + "Tu nombre es: " + nombre + "\n"
                + "Tu contraseña es: " + contrasenia);

    }

}
