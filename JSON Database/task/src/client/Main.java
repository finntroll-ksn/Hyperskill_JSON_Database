package client;

import com.beust.jcommander.JCommander;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(final String[] args) {
        Request request = new Request();
        JCommander.newBuilder()
                .addObject(request)
                .build()
                .parse(args);
        new Main().run(request);
    }

    private void run(Request request) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            String message = "";

            if (request.getInput() == null) {
                message = request.toJson();
            } else {
                try {
                    Path filepath = Paths.get("src/client/data/" + request.getInput());
                    message = String.join("\n", Files.readAllLines(filepath));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println("Sent: " + message);
            output.writeUTF(message);

            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
