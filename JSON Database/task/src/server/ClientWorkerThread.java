package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientWorkerThread implements Runnable {
    private Socket socket;
    private ServerSocket server;
    private DataBase database;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientWorkerThread(Socket socket, ServerSocket server, DataBase database) {
        this.socket = socket;
        this.server = server;
        this.database = database;

        try {
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String msg = dataInputStream.readUTF();
            JsonObject map = JsonParser.parseString(msg).getAsJsonObject();

            switch (map.get("type").getAsString()) {
                case "set":
                    dataOutputStream.writeUTF(database.set(map.get("key"), map.get("value")));

                    break;
                case "get":
                    dataOutputStream.writeUTF(database.get(map.get("key")));

                    break;
                case "delete":
                    dataOutputStream.writeUTF(database.delete(map.get("key")));

                    break;
                case "exit":
                    dataOutputStream.writeUTF("{\"response\":\"OK\"}");
                    socket.close();
                    server.close();

                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
