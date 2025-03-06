package vistaProyectoFinal.DWS_DIW.dtos;
/**
 * DTO para manejar la recuperación de contraseña de un usuario.
 * 
 * @author irodhan - 06/03/2025
 */
public class RecuperarPasswordDto {
	//Atributos
		private String emailUsuario;
		//Getters & Setters

		public String getEmailUsuario() {
			return emailUsuario;
		}

		public void setEmailUsuario(String emailUsuario) {
			this.emailUsuario = emailUsuario;
		}
}
