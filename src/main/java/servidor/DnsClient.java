package servidor;

import java.io.IOException;
import java.net.*;

public class DnsClient{
	private final String host;
	private final int port;

	public DnsClient(String host, int port) {
		this.host = host;
		this.port = port;
	}	


	public String consultar(String dominio) throws IOException{
		DatagramSocket socket = new DatagramSocket();
		InetAddress direccionServidorDNS = InetAddress.getByName(host);
		
		byte[] datos = dominio.getBytes();
		DatagramPacket paquete = new DatagramPacket(datos, datos.length, direccionServidorDNS, port);
		socket.send(paquete);
		
		byte[] buffer = new byte[512];
		DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
		socket.receive(respuesta);
		
		String ip = new String(respuesta.getData(), 0, respuesta.getLength()).trim();
		socket.close();
		
		return ip;
		
	}
}

