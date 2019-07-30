package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Commands {

    private String host;
    private static final int PORT = 4000;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;

    // As the host use the IP address of your server machine please

    public Client() throws IOException {
        System.out.print("Enter the connection address please: ");
        Scanner addressInput = new Scanner(System.in);
        host = addressInput.nextLine();

        socket = new Socket(host, PORT);
    }


    @Override
    public void start() throws IOException, InterruptedException {
        System.out.println("Connected to the server: " + host);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());



        Thread firstThread = new Thread(() -> {
            while (true) {
                try {
                    receiveMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        firstThread.start();

        Thread secondThread = new Thread(() -> {
            while (true) {
                try {
                    sendMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }

    // This method receives messages from the server

    @Override
    public void receiveMessage() throws IOException {
        String message;

        while (true) {
            message = dataInputStream.readUTF();
            System.out.println("Server says: " + message);

            if (message.equals("$exit")) {
                disconnect();
                break;
            }

            dataOutputStream.flush();
        }
    }

    // This method is for sending messages

    @Override
    public void sendMessage() throws IOException {
        String message;

        while (true) {
            Scanner scanner = new Scanner(System.in);
            message = scanner.nextLine();

            dataOutputStream.writeUTF(message);

            if (message.equals("$exit")) {
                disconnect();
                break;
            }
        }
    }

    // Disconnects from the server

    @Override
    public void disconnect() {
        System.out.println("Disconnecting");
        System.exit(0);
    }
}