
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("26.128.182.138", 1236);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader consoleReader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.print("Enter three numbers (a,b,c): ");
        String input = consoleReader.readLine();

        out.println(input);
        String response = in.readLine();
        System.out.println("Final result: " + response);

        socket.close();
    }
}