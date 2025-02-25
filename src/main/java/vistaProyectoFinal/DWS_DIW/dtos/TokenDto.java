package vistaProyectoFinal.DWS_DIW.dtos;

import java.time.LocalDateTime;

public class TokenDto {
	//Atributos
	private Long idToken;
    private String token;
    private LocalDateTime fechaExpiracion;
    public boolean estaExpirado() {
        return fechaExpiracion.isBefore(LocalDateTime.now());
    }
    //Getters & Setters
	public Long getIdToken() {
		return idToken;
	}
	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getFechaExpiracion() {
		return fechaExpiracion;
	}
	public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
    
}
