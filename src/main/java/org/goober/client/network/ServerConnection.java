package org.goober.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {

    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerConnection(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Connects to the server.
     *
     * @return true if the connection was successful, false otherwise
     */
    public boolean connect() {
        try {
            this.socket = new Socket(serverAddress, serverPort);
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sends a command to the server and returns the response.
     *
     * @param command The command to send
     * @return The server's response
     */
    public String sendCommand(String command) {
        if (socket == null || socket.isClosed()) {
            throw new IllegalStateException("Not connected to the server.");
        }

        try {
            out.println(command);
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error communicating with the server: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the connection to the server.
     */
    public void disconnect() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing the connection: " + e.getMessage());
            }
        }
    }
}

