package ru.chat.geekbrains.client;

import ru.chat.geekbrains.client.history.History;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {

    private History history;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new ClientConnectionException("SWW", e);
        }
    }
   private void createHistory(String nickname){
       String nameOfHistoryFile = String.format("history_%s.txt",nickname);
       this.history = new History (nameOfHistoryFile);

   }
   private String loadHistory(){
        if (history.isNotEmpty()){
            return history.loadMsgFromHistory(100);
        } else {

            return "History for current user is empty";
        }
   }


    public String receiveMessage() throws ClientConnectionException {
        try {
            String inMessage = in.readUTF();
            if (inMessage.startsWith("!!== ")){
                        createHistory(inMessage.split("\\s")[1]);
                        return loadHistory();
                    }else {
                    if(Objects.nonNull(history))
                        history.writeHistory(inMessage);
                        return inMessage;
                    }
        } catch (IOException e) {
            throw new ClientConnectionException("SWW", e);
        }
    }

    public void sendMessage(String message) throws ClientConnectionException {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new ClientConnectionException("SWW", e);
        }
    }

    public void close() {
        close(in);
        close(out);
    }

    private void close(Closeable stream) {
        if (stream == null) {
            return;
        }

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
