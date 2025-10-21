package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ServidorDNSApplication {

    public static void main(String[] args) throws IOException {
        int puerto = 8053; // 
        DatagramSocket socket = new DatagramSocket(puerto);
        System.out.println("Servidor DNS iniciado en puerto " + puerto);
        
        
        Map<String, String> zonas = new HashMap<>();
        zonas.put("example.com", "93.184.216.34");
        zonas.put("localhost", "127.0.0.1");
        zonas.put("midominio.com", "192.168.1.100");

        Map<InetAddress, Long> ultimaPeticion = new HashMap<>();
        
        byte[] buffer = new byte[512];

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);
            
            InetAddress cliente = request.getAddress();
            long ahora = System.currentTimeMillis();
            
            if(ultimaPeticion.containsKey(cliente)) {
            	long diff = ahora - ultimaPeticion.get(cliente);
            	if (diff < 100) {
            		System.out.println("Peticiones demasiado frecuentes desde " + cliente);
            		continue;
            	}
            }
            ultimaPeticion.put(cliente, ahora);

            String dominio = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8).trim();
            
            System.out.println("Consulta recibida para: " + dominio);
            
            if (!dominio.matches("^[a-zA-Z0-9.-]+$")) {
            	System.out.println("Consulta sospechosa: " + dominio);
            	continue;
            }

            String ip = zonas.getOrDefault(dominio, "0.0.0.0"); 
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
