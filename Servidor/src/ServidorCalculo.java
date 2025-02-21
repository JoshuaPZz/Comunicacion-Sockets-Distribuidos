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
                    try {

                        String input = in.readLine();
                        String[] parts = input.split(",");
                        double a = Double.parseDouble(parts[0]);
                        double b = Double.parseDouble(parts[1]);
                        double c = Double.parseDouble(parts[2]);
                        double sum = 0;
                        double product = 0;

                        System.out.println("Recibidos operandos a: " + a + ", b = " + b + ", c = " + c);

                        try {
                            // SUMA
                            Socket op1Socket = new Socket("10.43.96.14", 1234);
                            PrintWriter op1Out = new PrintWriter(op1Socket.getOutputStream(), true);
                            BufferedReader op1In = new BufferedReader(
                                    new InputStreamReader(op1Socket.getInputStream()));
                            System.out.println("Enviando " + a + " + " + b + " para suma");
                            op1Out.println(a + "," + b);
                            sum = Double.parseDouble(op1In.readLine());
                            System.out.println("Resultado : " + sum + " recibida");
                            System.out.println("Enviando resultado a servidor de multiplicacion...");
                            op1Socket.close();

                        } catch (Exception e) {
                            if (e instanceof ConnectException || e instanceof java.rmi.ConnectException) {
                                System.out.println("Error: No se pudo conectar con el servidor de suma");
                                System.out.println("Reemplazando a servidor de suma...");
                                sum = a + b;
                                System.out.println("Sumando " + a + " + " + b);                            }
                        }

                        try {
                            // MULTIPLICACION
                            Socket op2Socket = new Socket("10.43.96.14", 1235);
                            PrintWriter op2Out = new PrintWriter(op2Socket.getOutputStream(), true);
                            BufferedReader op2In = new BufferedReader(
                                    new InputStreamReader(op2Socket.getInputStream()));
                            System.out.println("Enviando " + sum + " y " + "c " + "a servidor de multiplicacion");
                            op2Out.println(sum + "," + c);
                            product = Double.parseDouble(op2In.readLine());
                            System.out.println("Resultado final: " + product + " recibido");
                            op2Socket.close();

                        } catch (Exception e) {
                            if (e instanceof ConnectException || e instanceof java.rmi.ConnectException) {
                                System.out.println("Error: No se pudo conectar con el servidor de multiplicacion");
                                System.out.println("Reemplazando a servidor de multiplicacion...");
                                product = sum * c;
                                System.out.println("Multiplicando " + sum + " y " + c);

                            }
                        
                        }
                        System.out.println("Enviando resultado a cliente...");
                        out.println(product);

                    } catch (Exception e) {
                        if (e instanceof NumberFormatException) {
                            System.out.println("Error: El formato que envio el cliente es incorrecto");
                            out.println("El formato que ingresaste es invalido :(");
                        } else if (e instanceof ArrayIndexOutOfBoundsException) {
                            System.out.println("Error: El cliente no envio la cantidad de numeros correcta");
                        } else if (e instanceof ConnectException || e instanceof java.rmi.ConnectException) {
                            System.out.println("Error: No se pudo conectar con el cliente");
                        }
                    }

                    clientSocket.close();
                } catch (Exception e) {

                }

            }).start();
        }
    }
}