package ru.chat.geekbrains.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class ServerChat implements Chat {
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
            clients = new HashSet<>();
            authenticationService = new AuthenticationService();

            while (true) {
                System.out.println("Server is waiting for a connection ...");
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                System.out.println(String.format("[%s] Client[%s] is successfully logged in", new Date(), clientHandler.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
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

    }

    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
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
