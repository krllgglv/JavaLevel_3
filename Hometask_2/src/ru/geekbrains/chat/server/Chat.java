package ru.geekbrains.chat.server;

public interface Chat {
    void broadcastMessage(String message);
    boolean isNicknameOccupied(String nickname);
    void subscribe(ClientHandler client);
    void unsubscribe(ClientHandler client);
    AuthenticationService getAuthenticationService();
    public void sendPrivateMessage(String message, ClientHandler clientHandler);

}
