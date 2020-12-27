package ru.geekbrains.chat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
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
    public void sendPrivateMessage(String message, ClientHandler clientHandler) {
        String[] messToArr = message.split("\\s");
        String destName = messToArr[1];
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < messToArr.length; i++) {
            sb.append(messToArr[i]).append(" ");
        }
        String privateMessage = sb.toString();
        if (getClientByNickname(clients,destName)!=null){

            getClientByNickname(clients,destName).sendMessage(String.format("[%s private 4u] : %s", clientHandler.getName(), privateMessage));
        } else {
            clientHandler.sendMessage(String.format("[%s] is offline. Cannot send message",  destName));
        }
    }
    private ClientHandler getClientByNickname(Set<ClientHandler> set, String name){
        for (ClientHandler s : set) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }
}
