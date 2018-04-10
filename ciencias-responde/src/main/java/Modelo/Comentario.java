package Modelo;
// Generated 9/04/2018 09:09:38 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Comentario generated by hbm2java
 */
public class Comentario  implements java.io.Serializable {


     private int idCom;
     private Pregunta pregunta;
     private Usuario usuario;
     private String contenido;
     private int votos;
     private Date fecha;

    public Comentario() {
    }

	
    public Comentario(int idCom, String contenido, int votos, Date fecha) {
        this.idCom = idCom;
        this.contenido = contenido;
        this.votos = votos;
        this.fecha = fecha;
    }
    public Comentario(int idCom, Pregunta pregunta, Usuario usuario, String contenido, int votos, Date fecha) {
       this.idCom = idCom;
       this.pregunta = pregunta;
       this.usuario = usuario;
       this.contenido = contenido;
       this.votos = votos;
       this.fecha = fecha;
    }
   
    public int getIdCom() {
        return this.idCom;
    }
    
    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }
    public Pregunta getPregunta() {
        return this.pregunta;
    }
    
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getContenido() {
        return this.contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public int getVotos() {
        return this.votos;
    }
    
    public void setVotos(int votos) {
        this.votos = votos;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}


