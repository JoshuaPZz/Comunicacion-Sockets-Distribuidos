
import java.io.*;
import java.net.*;

public class OperacionMulti {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1235);
        System.out.println("Server multiplicacion escuchando en puerto 1235...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String input = in.readLine();
                    String[] parts = input.split(",");
                    double a = Double.parseDouble(parts[0]);
                    double b = Double.parseDouble(parts[1]);
                    double result = a * b;
                    System.out.println("Multiplicando "+a+" con "+b);
                    System.out.println("El resultado es: "+result);
                    System.out.println("Mandando resultado...");

                    out.println(result);
                    clientSocket.close();
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}