package vistaProyectoFinal.DWS_DIW.dtos;
/**
 * DTO para representar los datos básicos de un usuario.
 * 
 * @author irodhan - 06/03/2025
 */
public class UsuarioDto {
    private Long idUsuario;
    private String nombreCompletoUsuario;
    private String telefonoUsuario;
    private String emailUsuario;
    private String rolUsuario; // Se agrega el rol

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
     * Obtiene el nombre completo del usuario.
     * @return Nombre completo del usuario.
     */
    public String getNombreCompletoUsuario() { return nombreCompletoUsuario; }
    
    /**
     * Establece el nombre completo del usuario.
     * @param nombreCompletoUsuario Nombre completo del usuario.
     */
    public void setNombreCompletoUsuario(String nombreCompletoUsuario) { this.nombreCompletoUsuario = nombreCompletoUsuario; }

    /**
     * Obtiene el teléfono del usuario.
     * @return Teléfono del usuario.
     */
    public String getTelefonoUsuario() { return telefonoUsuario; }
    
    /**
     * Establece el teléfono del usuario.
     * @param telefonoUsuario Teléfono del usuario.
     */
    public void setTelefonoUsuario(String telefonoUsuario) { this.telefonoUsuario = telefonoUsuario; }

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
    
    /**
     * Obtiene el rol del usuario.
     * @return Rol del usuario.
     */
    public String getRolUsuario() { return rolUsuario; }
    
    /**
     * Establece el rol del usuario.
     * @param rolUsuario Rol del usuario.
     */
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }
}
