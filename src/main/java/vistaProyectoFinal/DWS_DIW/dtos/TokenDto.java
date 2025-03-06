package vistaProyectoFinal.DWS_DIW.dtos;

import java.time.LocalDateTime;
/**
 * DTO para representar los datos de un token de autenticación.
 * 
 * @author irodhan - 06/03/2025
 */
public class TokenDto {
    /** Identificador único del token. */
    private Long idToken;
    /** Cadena de caracteres que representa el token. */
    private String token;
    /** Fecha y hora de expiración del token. */
    private LocalDateTime fechaExpiracion;

    /**
     * Verifica si el token ha expirado.
     * @return true si el token está expirado, false en caso contrario.
     */
    public boolean estaExpirado() {
        return fechaExpiracion.isBefore(LocalDateTime.now());
    }

    /**
     * Obtiene el ID del token.
     * @return ID del token.
     */
    public Long getIdToken() {
        return idToken;
    }
    
    /**
     * Establece el ID del token.
     * @param idToken ID del token.
     */
    public void setIdToken(Long idToken) {
        this.idToken = idToken;
    }
    
    /**
     * Obtiene la cadena del token.
     * @return Cadena del token.
     */
    public String getToken() {
        return token;
    }
    
    /**
     * Establece la cadena del token.
     * @param token Cadena del token.
     */
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * Obtiene la fecha de expiración del token.
     * @return Fecha de expiración del token.
     */
    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }
    
    /**
     * Establece la fecha de expiración del token.
     * @param fechaExpiracion Fecha de expiración del token.
     */
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
