package servidor;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServidorWebApplication {

	public static void main(String[] args) throws IOException{
		int puerto = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(puerto), 0);
		
		server.start();
		System.out.println("Servidor iniciado en puerto " + puerto);
	}

}
