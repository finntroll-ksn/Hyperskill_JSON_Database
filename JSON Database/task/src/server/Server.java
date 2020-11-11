package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

class Server {
    void run() {
        String address = "127.0.0.1";
        int port = 23456;
        DataBase dataBase = new DataBase();

        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {

            while (!server.isClosed()) {
                try {
                    Socket client = server.accept();

                    if (client != null) {
                        new Thread(new ClientWorkerThread(client, server, dataBase)).start();
                    }
                } catch (SocketException e) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
