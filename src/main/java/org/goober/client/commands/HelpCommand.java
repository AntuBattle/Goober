package org.goober.client.commands;

import org.goober.client.CommandHandler;

import java.util.Map;

public class HelpCommand implements Command {

    private final CommandHandler commandHandler;

    public HelpCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder helpMessage = new StringBuilder("Available commands:\n");
        for (Map.Entry<String, Command> entry : commandHandler.getCommands().entrySet()) {
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

