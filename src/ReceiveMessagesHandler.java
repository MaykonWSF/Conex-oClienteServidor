import java.io.*;
import java.net.*;

public class ReceiveMessagesHandler implements Runnable {
    private Socket socket;

    public ReceiveMessagesHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("Erro ao receber mensagens do servidor: " + e.getMessage());
        }
    }
}
