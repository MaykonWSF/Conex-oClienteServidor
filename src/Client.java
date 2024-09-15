import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String hostname = "127.0.0.1";
        int port = 12345;

        try (Socket socket = new Socket(hostname, port)) {
            new Thread(new ReceiveMessagesHandler(socket)).start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while ((message = consoleReader.readLine()) != null) {
                out.println(message);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Servidor não encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro de I/O: " + ex.getMessage());
        }
    }
}
