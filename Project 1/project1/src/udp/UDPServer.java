// COSC 331 Project 1
// 4/25/2025
// Authors: Jennifer Pumphrey & Tehila Morgan
package udp;

import java.net.*;
import java.util.Scanner;
import java.io.IOException;

public class UDPServer {

    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;

    public static void main(String[] args) {
        UDPServer server = new UDPServer();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws Exception {
        int port = 4004;
        socket = new DatagramSocket(port);

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Server is running...");
            System.out.println("IP Address: " + localHost.getHostAddress());
            System.out.println("Port: " + port);
            System.out.println("Type messages to send to client. To stop the server, type 'q' and press Enter.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Thread 1: Receiving messages from client
        Thread receiveThread = new Thread(() -> {
            try {
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    clientAddress = packet.getAddress();
                    clientPort = packet.getPort();

                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Client: " + message);
                }
            } catch (SocketException e) {
                System.out.println("Server socket closed. Exiting receive thread.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Thread 2: Sending messages to client AND handling shutdown
        Thread sendThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            try {
                while (true) {
                    String message = scanner.nextLine();
                    if (message.equalsIgnoreCase("q")) {
                        System.out.println("Shutting down server...");
                        socket.close(); // Causes receive thread to exit
                        break;
                    }

                    if (clientAddress != null) {
                        byte[] data = message.getBytes();
                        DatagramPacket packet = new DatagramPacket(data, data.length, clientAddress, clientPort);
                        socket.send(packet);
                    } else {
                        System.out.println("No client connected yet. Waiting for first message...");
                    }
                }
            } catch (SocketException e) {
                System.out.println("Server socket closed. Exiting send thread.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                scanner.close();
            }
        });

        // Start both threads
        receiveThread.start();
        sendThread.start();

        // Wait for threads to finish
        receiveThread.join();
        sendThread.join();

        System.out.println("Server fully stopped.");
    }
}

