package org.goober.server;

import org.goober.server.commands.Command;
import org.goober.server.commands.GetUserInfoServerCommand;
import org.goober.server.commands.RegisterServerCommand;
import org.goober.server.commands.SaveVaultServerCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private final Map<String, Command> commands;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put("register", new RegisterServerCommand(clientSocket));
        commands.put("get_user_info", new GetUserInfoServerCommand());
        commands.put("save_vault", new SaveVaultServerCommand());
        //commands.put("help", new HelpServerCommand(this));
        //commands.put("logout", new LogoutServerCommand(this));
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String response = handleCommand(inputLine.trim());
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            try {
                //TODO This should be done by the logout command.
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private String handleCommand(String input) {
        String[] parts = input.split("\\s+");
        String commandName = parts[0].toLowerCase();
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);

        Command command = commands.get(commandName);
        if (command == null) {
            return "Error: Unknown command '" + commandName + "'.";
        }

        return command.execute(args);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}

