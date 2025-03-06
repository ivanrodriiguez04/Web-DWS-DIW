package vistaProyectoFinal.DWS_DIW.dtos;
/**
 * DTO para representar los datos de registro de un usuario.
 * 
 * @author irodhan - 06/03/2025
 */
public class RegistroDto {
    /** Identificador único del usuario. */
    private long idUsuario;
    /** Nombre completo del usuario. */
    private String nombreCompletoUsuario;
    /** Teléfono del usuario. */
    private String telefonoUsuario;
    /** Rol del usuario, por defecto 'usuario'. */
    private String rolUsuario;
    /** Correo electrónico del usuario. */
    private String emailUsuario;
    /** Contraseña del usuario. */
    private String passwordUsuario;
    /** DNI del usuario. */
    private String dniUsuario;
    /** Imagen frontal del DNI del usuario. */
    private byte[] fotoDniFrontalUsuario;
    /** Imagen trasera del DNI del usuario. */
    private byte[] fotoDniTraseroUsuario;
    /** Foto del usuario. */
    private byte[] fotoUsuario;
    /** Indica si la cuenta ha sido confirmada. */
    private boolean confirmado;
    /** Token de confirmación. */
    private String token;

    /**
     * Constructor que inicializa los datos de un usuario registrado.
     * @param idUsuario ID del usuario.
     * @param nombreCompletoUsuario Nombre completo del usuario.
     * @param telefonoUsuario Teléfono del usuario.
     * @param emailUsuario Correo electrónico del usuario.
     * @param passwordUsuario Contraseña del usuario.
     * @param dniUsuario DNI del usuario.
     * @param fotoDniFrontalUsuario Imagen frontal del DNI del usuario.
     * @param fotoDniTraseroUsuario Imagen trasera del DNI del usuario.
     * @param fotoUsuario Foto del usuario.
     * @param token Token de confirmación.
     */
    public RegistroDto(long idUsuario, String nombreCompletoUsuario, String telefonoUsuario, String emailUsuario, 
                       String passwordUsuario, String dniUsuario, byte[] fotoDniFrontalUsuario, 
                       byte[] fotoDniTraseroUsuario, byte[] fotoUsuario, String token) {
        this.idUsuario = idUsuario;
        this.nombreCompletoUsuario = nombreCompletoUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.rolUsuario = "usuario"; // Valor por defecto
        this.emailUsuario = emailUsuario;
        this.passwordUsuario = passwordUsuario;
        this.dniUsuario = dniUsuario;
        this.fotoDniFrontalUsuario = fotoDniFrontalUsuario;
        this.fotoDniTraseroUsuario = fotoDniTraseroUsuario;
        this.fotoUsuario = fotoUsuario;
        this.confirmado = false; // Valor por defecto
        this.token = token;
    }

    // Getters y Setters
    public long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreCompletoUsuario() { return nombreCompletoUsuario; }
    public void setNombreCompletoUsuario(String nombreCompletoUsuario) { this.nombreCompletoUsuario = nombreCompletoUsuario; }
    public String getTelefonoUsuario() { return telefonoUsuario; }
    public void setTelefonoUsuario(String telefonoUsuario) { this.telefonoUsuario = telefonoUsuario; }
    public String getRolUsuario() { return rolUsuario; }
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }
    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
    public String getPasswordUsuario() { return passwordUsuario; }
    public void setPasswordUsuario(String passwordUsuario) { this.passwordUsuario = passwordUsuario; }
    public String getDniUsuario() { return dniUsuario; }
    public void setDniUsuario(String dniUsuario) { this.dniUsuario = dniUsuario; }
    public byte[] getFotoDniFrontalUsuario() { return fotoDniFrontalUsuario; }
    public void setFotoDniFrontalUsuario(byte[] fotoDniFrontalUsuario) { this.fotoDniFrontalUsuario = fotoDniFrontalUsuario; }
    public byte[] getFotoDniTraseroUsuario() { return fotoDniTraseroUsuario; }
    public void setFotoDniTraseroUsuario(byte[] fotoDniTraseroUsuario) { this.fotoDniTraseroUsuario = fotoDniTraseroUsuario; }
    public byte[] getFotoUsuario() { return fotoUsuario; }
    public void setFotoUsuario(byte[] fotoUsuario) { this.fotoUsuario = fotoUsuario; }
    public boolean isConfirmado() { return confirmado; }
    public void setConfirmado(boolean confirmado) { this.confirmado = confirmado; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
