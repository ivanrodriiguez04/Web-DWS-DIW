package vistaProyectoFinal.DWS_DIW.dtos;

public class SucursalDto {
    private Long idSucursal;
    private String ciudadSucursal;
    private String codigoBanco;
    private String codigoSucursal;
    private String direccionSucursal;

    // Getters y setters
    public Long getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Long idSucursal) { this.idSucursal = idSucursal; }

    public String getCiudadSucursal() { return ciudadSucursal; }
    public void setCiudadSucursal(String ciudadSucursal) { this.ciudadSucursal = ciudadSucursal; }

    public String getCodigoBanco() { return codigoBanco; }
    public void setCodigoBanco(String codigoBanco) { this.codigoBanco = codigoBanco; }

    public String getCodigoSucursal() { return codigoSucursal; }
    public void setCodigoSucursal(String codigoSucursal) { this.codigoSucursal = codigoSucursal; }

    public String getDireccionSucursal() { return direccionSucursal; }
    public void setDireccionSucursal(String direccionSucursal) { this.direccionSucursal = direccionSucursal; }
}
