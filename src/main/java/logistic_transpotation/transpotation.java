package logistic_transpotation;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class transpotation {
    private static List<Map<String, String>> requests = new ArrayList<>();
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);

        server.createContext("/add_request", new AddRequestHandler());
        server.createContext("/get_requests", new GetRequestsHandler());
        server.createContext("/calculate", new CalculateCostHandler());

        server.setExecutor(null);
        System.out.println("Server running at http://localhost:3000");
        server.start();
    }

    static class AddRequestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Map<String, String> requestData = gson.fromJson(requestBody, HashMap.class);

                requests.add(requestData);

                String response = gson.toJson(Map.of("message", "Request added successfully!"));
                sendResponse(exchange, response, 201);
            } else {
                sendResponse(exchange, "Method Not Allowed", 405);
            }
        }
    }

    static class GetRequestsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = gson.toJson(requests);
                sendResponse(exchange, response, 200);
            } else {
                sendResponse(exchange, "Method Not Allowed", 405);
            }
        }
    }

    static class CalculateCostHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Map<String, Double> requestData = gson.fromJson(requestBody, HashMap.class);

                double distance = requestData.getOrDefault("distance", 0.0);
                double cargoWeight = requestData.getOrDefault("cargoWeight", 0.0);
                double fuelPrice = requestData.getOrDefault("fuelPrice", 0.0);
                double fuelEfficiency = requestData.getOrDefault("fuelEfficiency", 0.0);

                if (distance <= 0 || cargoWeight <= 0 || fuelPrice <= 0 || fuelEfficiency <= 0) {
                    sendResponse(exchange, gson.toJson(Map.of("error", "All input values must be positive numbers.")), 400);
                    return;
                }

                double fuelNeeded = distance / fuelEfficiency;
                double fuelCost = fuelNeeded * fuelPrice;
                double cargoHandlingCost = cargoWeight * 0.5;
                double totalCost = fuelCost + cargoHandlingCost;

                String response = gson.toJson(Map.of("distance", distance, "cargoWeight", cargoWeight, "fuelPrice", fuelPrice, "fuelEfficiency", fuelEfficiency, "totalCost", totalCost));
                sendResponse(exchange, response, 200);
            } else {
                sendResponse(exchange, "Method Not Allowed", 405);
            }
        }
    }

    private static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
