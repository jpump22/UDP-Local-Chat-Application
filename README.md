# UDP Local Chat Application

A local network chat program that allows two users to communicate directly over the same network using the UDP protocol. The application was developed as part of a networking course project and demonstrates socket programming, client-server architecture, and concurrent message handling.

## Project Overview

This project implements a lightweight messaging system that allows two machines on the same network to exchange messages without relying on external messaging services.

The system follows a client-server architecture where:

- The server starts first and displays its IP address and port number.
- The client connects using the server's IP address and port.
- Messages are sent and received using UDP sockets.

Both the client and server support continuous communication, allowing either side to send multiple messages without waiting for responses.

## Technologies Used

- Java
- UDP Socket API (DatagramSocket / DatagramPacket)
- Client-Server Networking
- Multithreading
- Local Network Communication

## System Architecture

The program consists of two components:

### Server (UDPServer.java)

The server:

- Creates a UDP socket on a specified port
- Displays the server IP address and port number
- Receives messages from the client
- Sends responses back to the client
- Uses multithreading to handle sending and receiving messages simultaneously
- Allows the server to shut down by entering `q`

### Client (UDPClient.java)

The client:

- Prompts the user for the server IP address and port number
- Creates a UDP socket to communicate with the server
- Sends messages to the server
- Receives messages from the server
- Uses multithreading to support simultaneous sending and receiving
- Allows the client to exit by entering `q`

## My Contribution

This project was completed with a partner.

My primary responsibility was implementing the **server-side program**.

**Server Development Responsibilities**

- Implemented the UDP server using Java socket programming
- Created the server socket using `DatagramSocket`
- Implemented message receiving using `DatagramPacket`
- Implemented message sending back to the client
- Added multithreading to allow simultaneous sending and receiving
- Implemented server shutdown functionality using user input

## Repository Structure

```
UDP-Local-Chat
│
├── UDPServer.java
├── UDPClient.java
└── README.md
```

## How to Run the Program

### 1. Compile the programs

```
javac udp/UDPServer.java
javac udp/UDPClient.java
```

### 2. Start the server

```
java udp.UDPServer
```

The server will display its IP address and port number.

### 3. Start the client (on another machine)

```
java udp.UDPClient
```

Enter the server's IP address and port number when prompted.

### 4. Chat between client and server

Both the client and server can send messages continuously.

To exit the program, type:

```
q
```

## Networking Concepts Demonstrated

- UDP communication
- Socket programming
- Client-server architecture
- Multithreaded communication
- Local network messaging

## Project Demonstration

The application was demonstrated using two machines connected to the same network, with one running the server and the other running the client. This setup allowed real-time communication between the two systems using UDP sockets.
