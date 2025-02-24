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
        System.out.println("Intentando abrir el navegador...");
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Para Windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://localhost:8080/");
            }
            System.out.println("Navegador abierto exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
