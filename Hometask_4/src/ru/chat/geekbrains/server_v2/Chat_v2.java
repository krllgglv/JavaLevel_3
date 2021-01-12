package ru.chat.geekbrains.server_v2;

import ru.chat.geekbrains.server.AuthenticationService;

public interface Chat_v2 {
    void broadcastMessage(String message);
    boolean isNicknameOccupied(String nickname);
    void subscribe(ClientHandler_v2 client);
    void unsubscribe(ClientHandler_v2 client);
    AuthenticationService getAuthenticationService();
    public void sendPrivateMessage(String message, ClientHandler_v2 clientHandler,String destName);

}
