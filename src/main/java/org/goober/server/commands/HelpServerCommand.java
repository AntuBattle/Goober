package org.goober.server.commands;

import org.goober.server.ClientHandler;

import java.util.Map;

public class HelpServerCommand implements Command {

    private final ClientHandler clientHandler;

    public HelpServerCommand(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder helpMessage = new StringBuilder("Available commands:\n");
        for (Map.Entry<String, Command> entry : clientHandler.getCommands().entrySet()) {
            helpMessage.append("  ").append(entry.getKey())
                    .append(" - ").append(entry.getValue().getDescription())
                    .append("\n");
        }
        return helpMessage.toString();
    }

    @Override
    public String getDescription() {
        return "Displays a list of available commands.";
    }

    @Override
    public String getUsage() {
        return "Usage: help";
    }
}

