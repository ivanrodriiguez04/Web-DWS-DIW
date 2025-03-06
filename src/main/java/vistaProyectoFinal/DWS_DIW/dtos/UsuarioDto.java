package vistaProyectoFinal.DWS_DIW.dtos;
/**
 * DTO para representar los datos básicos de un usuario.
 * 
 * @author irodhan - 06/03/2025
 */
public class UsuarioDto {
    private Long idUsuario;
    private String nombreUsuario;
    private String emailUsuario;

    /**
     * Obtiene el ID del usuario.
     * @return ID del usuario.
     */
    public Long getIdUsuario() { return idUsuario; }
    
    /**
     * Establece el ID del usuario.
     * @param idUsuario ID del usuario.
     */
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    /**
     * Obtiene el nombre del usuario.
     * @return Nombre del usuario.
     */
    public String getNombreUsuario() { return nombreUsuario; }
    
    /**
     * Establece el nombre del usuario.
     * @param nombreUsuario Nombre del usuario.
     */
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return Correo electrónico del usuario.
     */
    public String getEmailUsuario() { return emailUsuario; }
    
    /**
     * Establece el correo electrónico del usuario.
     * @param emailUsuario Correo electrónico del usuario.
     */
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
}