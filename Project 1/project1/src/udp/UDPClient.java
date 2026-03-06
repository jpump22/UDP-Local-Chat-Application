// COSC 331 Project 1
// 4/25/2025
// Authors: Jennifer Pumphrey & Tehila Morgan
package udp;

import java.net.*;
import java.util.Scanner;
import java.io.IOException;

public class UDPClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter server IP address: ");
            String serverIP = scanner.nextLine();

            System.out.print("Enter server port number: ");
            int serverPort = Integer.parseInt(scanner.nextLine());

            InetAddress serverAddress = InetAddress.getByName(serverIP);
            DatagramSocket socket = new DatagramSocket();

            System.out.println("Connected to server at " + serverIP + ":" + serverPort);
            System.out.println("Type your messages below. To exit, type 'q' and press Enter.");

            // Thread 1: Sending messages to server
            Thread sendThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = scanner.nextLine();

                        if (message.equalsIgnoreCase("q")) {
                            System.out.println("Client exiting...");
                            socket.close();  // This will also trigger the receive thread to exit
                            break;
                        }

                        byte[] buffer = message.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                        socket.send(packet);
                    }
                } catch (IOException e) {
                    System.out.println("Send thread closed.");
                }
            });

            // Thread 2: Receiving messages from server
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        byte[] buffer = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);

                        String message = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("Server: " + message);
                    }
                } catch (SocketException e) {
                    System.out.println("Receive thread closed (socket closed).");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            sendThread.start();
            receiveThread.start();

            sendThread.join();
            receiveThread.join();

            scanner.close();
            System.out.println("Client fully exited.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

