package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ServidorDNSApplication {

    public static void main(String[] args) throws IOException {
        int puerto = 8053; // Usa 53 si tienes permisos root
        DatagramSocket socket = new DatagramSocket(puerto);
        System.out.println("Servidor DNS iniciado en puerto " + puerto);

        // Zona de dominios -> IPs (simulación de tabla DNS)
        Map<String, String> zonas = new HashMap<>();
        zonas.put("example.com", "93.184.216.34");
        zonas.put("localhost", "127.0.0.1");
        zonas.put("midominio.com", "192.168.1.100");

        byte[] buffer = new byte[512];

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            String dominio = new String(request.getData(), 0, request.getLength()).trim();
            System.out.println("Consulta recibida para: " + dominio);

            String ip = zonas.getOrDefault(dominio, "0.0.0.0"); // respuesta por defecto
            byte[] respuesta = ip.getBytes();

            DatagramPacket reply = new DatagramPacket(
                    respuesta,
                    respuesta.length,
                    request.getAddress(),
                    request.getPort()
            );

            socket.send(reply);
            System.out.println("Respuesta enviada: " + ip);
        }
    }
}

