package ar.com.leo.monitor.http;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class NautilusHttpServer {
  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/machines/stop", new StopMachinesHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
    System.out.println("Server is running on port 8000");
  }

  static class StopMachinesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      if ("POST".equals(exchange.getRequestMethod())) {
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String requestBody = new String(requestBytes);
        Gson gson = new Gson();
        Machine[] machines = gson.fromJson(requestBody, Machine[].class);

        // TODO: Process machines array here
        System.out.println(machines);
        
        String response = "Machines received. Sending to stop...";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
      } else {
        exchange.sendResponseHeaders(405, -1); // Method Not Allowed
      }
    }
  }
}
