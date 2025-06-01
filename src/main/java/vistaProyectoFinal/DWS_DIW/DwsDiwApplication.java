package vistaProyectoFinal.DWS_DIW;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DwsDiwApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DwsDiwApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo abre navegador si está corriendo embebido (no en WAR)
        if (isRunningLocally()) {
            System.out.println("Intentando abrir el navegador...");
            try {
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://localhost:8080/");
                }
                System.out.println("Navegador abierto exitosamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isRunningLocally() {
        String server = System.getProperty("spring.profiles.active");
        return server == null || server.equals("local");
    }
}
