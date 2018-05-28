/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Usuario;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.persistence.Query;
import static org.apache.commons.codec.binary.Base64.decodeBase64;

/**
 *
 * @author Faded
 */
@ManagedBean(name = "sesion")
@SessionScoped
public class Sesion {
    // Definici�n del tipo de algoritmo a utilizar (AES, DES, RSA)
    private final static String alg = "AES";
    // Definici�n del modo de cifrado a utilizar
    private final static String cI = "AES/CBC/PKCS5Padding";
    
    private Usuario usuario;
    
    /**
     * Creates a new instance of Sesion
     */
    public Sesion() {
        usuario = new Usuario();
    }
    
    
    public Usuario getusuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String iniciarSesion() throws Exception {
        Usuario l = encuentraUsuario(usuario.getCorreo(), usuario.getContrasenia());
        boolean logged = l != null;
        if (logged) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", l);
            return "PantallaPrincipalIH.xhtml?faces-redirect=true";
        }

        return "InicioSesionIH.xhtml?faces-redirect=true";
    }
    
    public String cerrarSesion() {
        System.out.println("chingados");
        FacesContext context = getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "PantallaPrincipalIH.xhtml?faces-redirect=true";
    }
    
    public boolean estaConectado() {
        FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return l != null;
    }
    
    public static Usuario getUsuario() {
        FacesContext context = getCurrentInstance();
        return (Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }
    
    public Usuario encuentraUsuario(String correo, String contrasenia) throws Exception {
        String key = "92AE31A79FEEB2A3"; //llave
        String iv = "0123456789ABCDEF"; // vector de inicializaci�n
        ConexionBD.conectarBD();
        Query q = ConexionBD.consultarBD("Usuario.findByCorreo")
                .setParameter("correo", correo);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        Usuario usr = (Usuario) q.getSingleResult();
        if(contrasenia.equals(decrypt(key,iv,usr.getContrasenia())))
            return usr;
        else{
            return null;
        }
    }
    
    public static String decrypt(String key, String iv, String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(cI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        byte[] enc = decodeBase64(encrypted);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }
}
