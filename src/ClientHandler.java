import java.io.*;
import java.net.*;
import java.util.Set;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Set<ClientHandler> clientHandlers;

    public ClientHandler(Socket clientSocket, Set<ClientHandler> clientHandlers) {
        this.clientSocket = clientSocket;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Bem-vindo ao bate-papo! Digite sua mensagem:");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Mensagem recebida: " + message);
                broadcastMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Erro de comunicação com o cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar o socket: " + e.getMessage());
            }
            clientHandlers.remove(this);
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != this) {
                clientHandler.out.println("Mensagem de outro cliente: " + message);
            }
        }
    }
}
