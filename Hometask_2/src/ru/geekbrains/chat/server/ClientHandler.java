package ru.geekbrains.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {
    private String name;
    private String login;
    private String password;

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private Chat chat;
    private Thread thread1;

    public ClientHandler(Socket socket, Chat chat) {
        this.socket = socket;
        this.chat = chat;
        thread1 = null;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }

        listen();
    }

    public String getName() {
        return name;
    }

    private void listen() {
        thread1 = new Thread(() -> {
            doAuth();
            receiveMessage();
        });
        thread1.start();
    }

    private void doAuth() {
        sendMessage("Please enter credentials. Sample [-auth login password]");
        try {
            /**
             * -auth login password
             * sample: -auth l1 p1
             */


            socket.setSoTimeout(10000);
            while (true) {
                String mayBeCredentials = in.readUTF();
                if (mayBeCredentials.startsWith("-auth")) {
                    String[] credentials = mayBeCredentials.split("\\s");
                    String mayBeNickname = chat.getAuthenticationService()
                            .findNicknameByLoginAndPassword(credentials[1], credentials[2]);
                    if (mayBeNickname != null) {
                        if (!chat.isNicknameOccupied(mayBeNickname)) {
                            sendMessage("[INFO] Auth OK");
                            login = credentials[1];
                            password = credentials[2];
                            name = mayBeNickname;
                            socket.setSoTimeout(0);

                            chat.broadcastMessage(String.format("[%s] logged in", name));
                            chat.subscribe(this);

                            return;
                        } else {
                            sendMessage("[INFO] Current user is already logged in.");
                        }
                    } else {
                        sendMessage("[INFO] Wrong login or password.");
                    }
                }

            }
        } catch (SocketTimeoutException e) {
            try {
                sendMessage("_____________________________________");
                sendMessage("Time for authentification is over!!" + "\n" +
                        "Please type \"-retry\" to try again" +"\n"+
                        "or \"-exit\" to disconnect");

                thread1.interrupt();
                socket.setSoTimeout(0);
            } catch (SocketException socketException) {
                socketException.printStackTrace();
            }

            while (true) {
                try {
                    String retry = in.readUTF();
                    if (retry.equals("-retry")) {
                        sendMessage("Retry auth");
                        doAuth();
                        return;
                    }
                    if (retry.equals("-exit")) {
                        socket.close();
                        sendMessage("Bye!!");
                        return;
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (Exception e) {

            throw new RuntimeException("SWW", e);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public void receiveMessage() {
        while (true) {
            try {
                String message = in.readUTF();
                if (message.startsWith("-exit")) {
                    chat.unsubscribe(this);
                    chat.broadcastMessage(String.format("[%s] logged out", name));
                    break;
                }
                if (message.startsWith("-pm")) {
                    String[] messToArr = message.split("\\s");
                    String destName = messToArr[1];
                    StringBuilder sb = new StringBuilder();
                    for (int i = 2; i < messToArr.length; i++) {
                        sb.append(messToArr[i]).append(" ");
                    }
                    String privateMessage = sb.toString();
                    sendMessage("U private 4 "+destName+" :"+ privateMessage);
                    chat.sendPrivateMessage(message, this);
                }
                if  (message.startsWith("-cn")) {
                    String[] messToArr = message.split("\\s");
                        String oldNick = name;
                    name=chat.getAuthenticationService().changeNickname(login,password,messToArr[1]);
                        chat.broadcastMessage(String.format("[%s] changed nick to [%s]", oldNick,name));

                }
                else {
                    chat.broadcastMessage(String.format("[%s]: %s", name, message));
                }
            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            }
        }
    }
}
