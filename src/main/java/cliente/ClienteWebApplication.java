package cliente;

import java.net.Socket;


public class ClienteWebApplication {
	
	public static void main(String [] args) {
		
		String host ="localhost";
		int port = 5000;
		
		try (Socket socket = new Socket(host, port)){
			System.out.println("Conexión establecida");
		} catch (Exception e) {
			System.out.println("Conexión no establecida");
		}
		
	}

}
