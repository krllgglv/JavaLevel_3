package ru.chat.geekbrains.server_v2;

import ru.chat.geekbrains.server.AuthenticationService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerChat_v2 implements Chat_v2{




        private ServerSocket serverSocket;
        private Set<ClientHandler_v2> clients;
        private AuthenticationService authenticationService;
    private ExecutorService executorService;

        public ServerChat_v2() {
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
                executorService = Executors.newCachedThreadPool();

                while (true) {
                    System.out.println("Server is waiting for a connection ...");
                    Socket socket = serverSocket.accept();
                    ClientHandler_v2 clientHandler;
                    executorService.submit(clientHandler = new ClientHandler_v2(socket, this));
                    System.out.println(String.format("[%s] Client[%s] is successfully logged in", new Date(), clientHandler.getName()));
//                    System.out.println(Thread.activeCount());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public synchronized void broadcastMessage(String message) {
            for (ClientHandler_v2 client : clients) {
                client.sendMessage(message);
            }
        }

        @Override
        public synchronized boolean isNicknameOccupied(String nickname) {
            for (ClientHandler_v2 client : clients) {
                if (client.getName().equals(nickname)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public synchronized void subscribe(ClientHandler_v2 client) {
            clients.add(client);

        }

        @Override
        public synchronized void unsubscribe(ClientHandler_v2 client) {
            clients.remove(client);
        }
        @Override
        public void sendPrivateMessage(String privateMessage, ClientHandler_v2 clientHandler, String destName) {
            ClientHandler_v2 dest = getClientByNickname(destName);
            if (Objects.nonNull(dest)){

                dest.sendMessage(String.format("[%s private 4u] : %s", clientHandler.getName(), privateMessage));
            } else {
                clientHandler.sendMessage(String.format("[%s] is offline. Cannot send message",  destName));
            }
        }
        private ClientHandler_v2 getClientByNickname( String name){
            for (ClientHandler_v2 s : clients) {
                if (s.getName().equals(name)) return s;
            }
            return null;
        }
    }


