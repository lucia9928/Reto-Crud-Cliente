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
 * Representa un usuario en el sistema.
 * <p>
 * Contiene información personal del usuario, incluyendo su DNI, nombre, email y dirección.
 * Es una clase base que puede ser extendida por otros tipos de usuarios, como trabajadores o clientes.
 * </p>
 * 
 * @author Melany
 */
@XmlRootElement(name = "usuario")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /** Documento Nacional de Identidad (DNI) del usuario. */
    private String dni;
    
    /** Nombre del usuario. */
    private String nombre;
    
    /** Apellido del usuario. */
    private String apellido;
    
    /** Correo electrónico del usuario. */
    private String email;
    
    /** Contraseña del usuario (debe almacenarse de forma segura). */
    private String contrasena;
    
    /** Fecha de nacimiento del usuario. */
    private Date fechaNacimiento;
    
    /** Calle de la dirección del usuario. */
    private String calle;
    
    /** Código postal de la dirección del usuario. */
    private Integer codigoPosta;
    
    /** Ciudad de residencia del usuario. */
    private String cidudad;

    /**
     * Obtiene el DNI del usuario.
     * 
     * @return El DNI del usuario.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del usuario.
     * 
     * @param dni Nuevo DNI del usuario.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * 
     * @param nombre Nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     * 
     * @return Apellido del usuario.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del usuario.
     * 
     * @param apellido Nuevo apellido del usuario.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return Correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     * 
     * @param email Nuevo correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return Contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param contrasena Nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * 
     * @return Fecha de nacimiento del usuario.
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     * 
     * @param fechaNacimiento Nueva fecha de nacimiento del usuario.
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene la calle de la dirección del usuario.
     * 
     * @return Calle de la dirección.
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Establece la calle de la dirección del usuario.
     * 
     * @param calle Nueva calle de la dirección.
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtiene el código postal de la dirección del usuario.
     * 
     * @return Código postal.
     */
    public Integer getCodigoPosta() {
        return codigoPosta;
    }

    /**
     * Establece el código postal de la dirección del usuario.
     * 
     * @param codigoPostal Nuevo código postal.
     */
    public void setCodigoPosta(Integer codigoPosta) {
        this.codigoPosta = codigoPosta;
    }

    /**
     * Obtiene la ciudad de residencia del usuario.
     * 
     * @return Ciudad de residencia.
     */
    public String getCidudad() {
        return cidudad;
    }

    /**
     * Establece la ciudad de residencia del usuario.
     * 
     * @param ciudad Nueva ciudad de residencia.
     */
    public void setCidudad(String ciudad) {
        this.cidudad = ciudad;
    }

    /**
     * Genera un código hash basado en el DNI del usuario.
     * 
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return (dni != null ? dni.hashCode() : 0);
    }

    /**
     * Compara este objeto con otro para verificar si son iguales.
     * <p>
     * Se considera que dos usuarios son iguales si tienen el mismo DNI.
     * </p>
     * 
     * @param object Objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return this.dni != null && this.dni.equals(other.dni);
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Usuario}.
     * 
     * @return Cadena que representa al usuario.
     */
    @Override
    public String toString() {
        return "Usuario{dni=" + dni + ", nombre=" + nombre + ", email=" + email + "}";
    }
}
