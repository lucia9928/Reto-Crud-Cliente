/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author melany
 */
@XmlRootElement(name="usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private Date fechaNacimiento;
    private String calle;
    private Integer codigoPosta;
    private String cidudad;
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getCodigoPosta() {
        return codigoPosta;
    }

    public void setCodigoPosta(Integer codigoPosta) {
        this.codigoPosta = codigoPosta;
    }

    public String getCidudad() {
        return cidudad;
    }

    public void setCidudad(String cidudad) {
        this.cidudad = cidudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dni != null ? dni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return !((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni)));
    }

    @Override
    public String toString() {
        return "cliente-eus.tartangalh.crud.create.Usuario[ dni=" + dni + " ]";
    }
    
}
