import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("IP DEL SERVIDOR DE CALCULO", 1236);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader consoleReader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.print("Ingresa tres numeros en el siguiente formato: 'a,b,c' (la operacion se realizara de la forma (a+b)*c): ");
        String input = consoleReader.readLine();

        out.println(input);
        String response = in.readLine();
        System.out.println("Resultado Final: " + response);

        socket.close();
    }
}