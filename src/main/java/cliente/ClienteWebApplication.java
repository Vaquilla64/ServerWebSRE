package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteWebApplication {
    public static void main(String[] args) throws Exception {
        String dominio = "google.com";
        String urlServidor = "http://localhost:8080/?dominio=" + dominio;

        URL url = new URL(urlServidor);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");

        BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String linea;
        while ((linea = lector.readLine()) != null) {
            System.out.println(linea);
            System.out.println("Se ha ejecutado una lectura de línea");
        }
        lector.close();
        conexion.disconnect();
    }
}


