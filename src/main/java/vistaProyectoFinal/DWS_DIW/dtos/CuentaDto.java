package vistaProyectoFinal.DWS_DIW.dtos;
/**
 * DTO para representar los datos de una cuenta bancaria.
 * 
 * @author irodhan - 06/03/2025
 */
public class CuentaDto {
    private Long idCuenta;
    private String nombreCuenta;
    private String tipoCuenta;
    private String ibanCuenta;
    private Double dineroCuenta;
    private String emailUsuario; // Se usa en la creación
    private Long idUsuario; // Nuevo campo agregado

    /**
     * Obtiene el ID de la cuenta.
     * @return ID de la cuenta.
     */
    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }

    /**
     * Obtiene el nombre de la cuenta.
     * @return Nombre de la cuenta.
     */
    public String getNombreCuenta() { return nombreCuenta; }
    public void setNombreCuenta(String nombreCuenta) { this.nombreCuenta = nombreCuenta; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getIbanCuenta() { return ibanCuenta; }
    public void setIbanCuenta(String ibanCuenta) { this.ibanCuenta = ibanCuenta; }

    public Double getDineroCuenta() { return dineroCuenta; }
    public void setDineroCuenta(Double dineroCuenta) { this.dineroCuenta = dineroCuenta; }

    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }

    public Long getIdUsuario() { return idUsuario; }  
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }  
}
