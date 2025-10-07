package servidor;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class DnsHandler implements HttpHandler{
	
	private final DnsClient dnsClient;
	
	public DnsHandler(DnsClient dnsClient) {
		this.dnsClient = dnsClient;
	}
	
	public void handle(HttpExchange exchange) throws IOException{
		String query = exchange.getRequestURI().getQuery();
		Map<String, String> params = queryToMap(query);
		String dominio = params.get("dominio");
		
		String respuesta;
		if(dominio == null || dominio.isEmpty()) {
			respuesta = "Error: no hay parámetro de dominio";
		}else {
			System.out.println("consultando DNS para: " + dominio);
			try {
				String ip = dnsClient.consultar(dominio);
				respuesta = "la IP para " + dominio + "es: " + ip;
			}catch(IOException e) {
				e.printStackTrace();
				respuesta = "Error al consultar el servidor DNS";
			}
		}
		
		exchange.sendResponseHeaders(200, respuesta.getBytes().length);
		try(OutputStream os = exchange.getResponseBody()){
			os.write(respuesta.getBytes());
		}
	}
	
	private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null) return result;
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}