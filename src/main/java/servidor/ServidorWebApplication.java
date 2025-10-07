package servidor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;


public class ServidorWebApplication {

	public static void main(String[] args) throws IOException{
		int puertoWeb = 8080;
		int puertoDNS = 8053;
		String hostDNS = "localhost";
		
		DnsClient dnsClient = new DnsClient(hostDNS, puertoDNS);
		
		HttpServer server = HttpServer.create(new InetSocketAddress(puertoWeb), 0);
		
		//Handler para la ruta principal
		server.createContext("/", new DnsHandler(dnsClient));
		
		server.start();
		System.out.println("Servidor iniciado en puerto " + puertoWeb);
	}

	
	
}
