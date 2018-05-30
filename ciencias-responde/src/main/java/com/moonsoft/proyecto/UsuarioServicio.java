/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moonsoft.proyecto;

import com.moonsoft.proyecto.model.ConexionBD;
import com.moonsoft.proyecto.model.Usuario;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import org.primefaces.model.UploadedFile;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author gouen
 */
@ManagedBean(name = "usuarioServicio")
@ViewScoped
public class UsuarioServicio {

    // Definición del tipo de algoritmo a utilizar (AES, DES, RSA)
    private final static String alg = "AES";
    // Definición del modo de cifrado a utilizar
    private final static String cI = "AES/CBC/PKCS5Padding";
    private String correo;
    private String nombre;
    private String contrasenia;
    private boolean esAdmin;
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public void fileUploadListener(FileUploadEvent e) {
        this.file = e.getFile();
    }
    
    /**
     * Método que se agrega un usuario a la base de datos.
     * 
     * @return enlace
     */
    public String agregarUsuario() throws Exception {
        try {
            String key = "92AE31A79FEEB2A3"; //llave
            String iv = "0123456789ABCDEF"; // vector de inicialización
            String respuesta = "";
            Pattern correoVal = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "ciencias\\.unam\\.mx$");
            Matcher mather = correoVal.matcher(correo);
            if (contrasenia.length() >= 8) {
                if (mather.find() == true) {
                    ConexionBD.conectarBD();
                    Query q = ConexionBD.consultarBD("Usuario.findByCorreo");
                    q.setParameter("correo", correo);
                    if (!q.getResultList().isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El usuario ya existe."));
                        return "";
                    }
                    Usuario usr = new Usuario(0, correo, nombre, encrypt(key, iv, contrasenia), new Date(), false);

                    if (file != null) {
                        if (file.getContents() == null) {
                            System.out.println("Diego es dios");
                        }
                        usr.setFoto(file.getContents());
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La imagen no debe ser vacioa"));
                    }
                    Email em = new Email(correo, nombre, contrasenia);
                    em.sendEmail();
                    usr.guardarBD();
                    respuesta = "RegistroExitosoIH.xhtml?faces-redirect=true";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El correo debe tener dominio @ciencias.unam.mx"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La contraseÃ±a de contener al menos 8 caracteres"));
            }
            return respuesta;
        } catch (Exception n) {
            return "ErrorConexionIH.xhtml?faces-redirect=true";
        }
    }
    
    /**
     * Método que se encarga de cifrar una contrasenia.
     *
     * @param key
     * @param iv
     * @param cleartext
     * @return contrasenia
     */
    public static String encrypt(String key, String iv, String cleartext) throws Exception {
        Cipher cipher = Cipher.getInstance(cI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        return new String(encodeBase64(encrypted));
    }
}
