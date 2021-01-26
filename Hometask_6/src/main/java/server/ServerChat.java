package server;

import org.apache.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class ServerChat implements Chat {
    private static final Logger logger = Logger.getLogger(ServerChat.class);
    private ServerSocket serverSocket;
    private Set<ClientHandler> clients;
    private AuthenticationService authenticationService;

    public ServerChat() {
        start();
    }


    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    private void start() {
        try {
            serverSocket = new ServerSocket(9998);
            String startMessage = "Server started";
            logger.info(startMessage);
            clients = new HashSet<>();
            authenticationService = new AuthenticationService();

            while (true) {

                System.out.println("Server is waiting for a connection ...");
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                System.out.println(String.format("[%s] Client[%s] is successfully logged in", new Date(), clientHandler.getName()));
            }
        } catch (Exception e) {
            logger.error("An error :" +e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
        logger.info(message);
    }

    @Override
    public synchronized boolean isNicknameOccupied(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getName().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
        logger.info(client.getName()+ "connected to server");

    }

    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
        logger.info(client.getName() + "disconnected from server");
    }
    @Override
    public void sendPrivateMessage(String privateMessage, ClientHandler clientHandler, String destName) {
            ClientHandler dest = getClientByNickname(destName);
        if (Objects.nonNull(dest)){

            dest.sendMessage(String.format("[%s private 4u] : %s", clientHandler.getName(), privateMessage));
        } else {
            clientHandler.sendMessage(String.format("[%s] is offline. Cannot send message",  destName));
        }
    }
    private ClientHandler getClientByNickname( String name){
        for (ClientHandler s : clients) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }
}
