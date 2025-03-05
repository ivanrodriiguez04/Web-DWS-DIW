package vistaProyectoFinal.DWS_DIW.dtos;


public class CuentaDto {
    private Long idCuenta;
    private String nombreCuenta;
    private String tipoCuenta;
    private String ibanCuenta;
    private Double dineroCuenta;
    private Long idUsuario;

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getIbanCuenta() {
        return ibanCuenta;
    }

    public void setIbanCuenta(String ibanCuenta) {
        this.ibanCuenta = ibanCuenta;
    }

    public Double getDineroCuenta() {
        return dineroCuenta;
    }

    public void setDineroCuenta(Double dineroCuenta) {
        this.dineroCuenta = dineroCuenta;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
