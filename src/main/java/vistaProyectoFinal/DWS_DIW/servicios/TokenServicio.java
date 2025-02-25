package vistaProyectoFinal.DWS_DIW.servicios;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import vistaProyectoFinal.DWS_DIW.dtos.RegistroDto;

@Service
public class TokenServicio {

    private Map<String, RegistroDto> tokensPendientes = new HashMap<>();

    public void guardarTokenTemporal(RegistroDto registroDto, String token) {
        tokensPendientes.put(token, registroDto);
    }

    public RegistroDto obtenerRegistroPorToken(String token) {
        return tokensPendientes.get(token);
    }

    public void eliminarToken(String token) {
        tokensPendientes.remove(token);
    }
}
