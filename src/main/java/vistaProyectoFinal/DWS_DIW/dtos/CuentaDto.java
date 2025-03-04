package vistaProyectoFinal.DWS_DIW.dtos;

public class CuentaDto {
    private long idCuenta;
    private String nombreCuenta;
    private String tipoCuenta;
    private String ibanCuenta;
    private int dineroCuenta;
    private long idUsuario;

    public CuentaDto() {}

    public CuentaDto(long idCuenta, String nombreCuenta, String tipoCuenta, String ibanCuenta, int dineroCuenta, long idUsuario) {
        this.idCuenta = idCuenta;
        this.nombreCuenta = nombreCuenta;
        this.tipoCuenta = tipoCuenta;
        this.ibanCuenta = ibanCuenta;
        this.dineroCuenta = dineroCuenta;
        this.idUsuario = idUsuario;
    }

    public long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(long idCuenta) {
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

    public int getDineroCuenta() {
        return dineroCuenta;
    }

    public void setDineroCuenta(int dineroCuenta) {
        this.dineroCuenta = dineroCuenta;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
