
import java.io.*;
import java.net.*;

public class ServidorCalculo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1236);
        System.out.println("Servidor principal escuchando en puerto 1236...");

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
                    double c = Double.parseDouble(parts[2]);


                    // SUMA
                    Socket op1Socket = new Socket("IP DE SUMA O LOCALHOST", 1234);
                    PrintWriter op1Out = new PrintWriter(op1Socket.getOutputStream(), true);
                    BufferedReader op1In = new BufferedReader(
                            new InputStreamReader(op1Socket.getInputStream()));
                    op1Out.println(a + "," + b);
                    double sum = Double.parseDouble(op1In.readLine());
                    op1Socket.close();

                    // MULTIPLICACION
                    Socket op2Socket = new Socket("IP DE MULTIPLICACION O LOCALHOST", 1235);
                    PrintWriter op2Out = new PrintWriter(op2Socket.getOutputStream(), true);
                    BufferedReader op2In = new BufferedReader(
                            new InputStreamReader(op2Socket.getInputStream()));
                    op2Out.println(sum + "," + c);
                    double product = Double.parseDouble(op2In.readLine());
                    op2Socket.close();

                    out.println(product);
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}