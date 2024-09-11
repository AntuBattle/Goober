package org.goober.client;

import org.goober.client.commands.Command;
import org.goober.client.commands.DeletePasswordCommand;
import org.goober.client.commands.HelpCommand;
import org.goober.client.commands.LoginCommand;
import org.goober.client.commands.LogoutCommand;
import org.goober.client.commands.ModifyPasswordCommand;
import org.goober.client.commands.RegisterCommand;
import org.goober.client.commands.SavePasswordCommand;
import org.goober.client.commands.SaveVaultCommand;
import org.goober.client.commands.ShowPasswordCommand;
import org.goober.client.commands.ShowVaultCommand;
import org.goober.client.model.UserModel;
import org.goober.client.network.ServerConnection;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final Map<String, Command> commands;
    private final UserModel currentUser;

    public CommandHandler(ServerConnection serverConnection, UserModel currentUser) {
        // Register commands
        this.commands = new HashMap<>();
        this.currentUser = currentUser;
        this.initializeCommands(serverConnection);
    }

    public void initializeCommands(ServerConnection serverConnection){
        commands.put("login", new LoginCommand(serverConnection, currentUser));
        commands.put("help", new HelpCommand(this));
        commands.put("register", new RegisterCommand(serverConnection, currentUser));
        commands.put("show_vault", new ShowVaultCommand(currentUser));
        commands.put("modify_password", new ModifyPasswordCommand(currentUser));
        commands.put("show_password", new ShowPasswordCommand(currentUser));
        commands.put("delete_password", new DeletePasswordCommand(currentUser));
        commands.put("save_password", new SavePasswordCommand(currentUser));
        commands.put("save_vault", new SaveVaultCommand(serverConnection, currentUser));
        commands.put("logout", new LogoutCommand(serverConnection));
    }

    public String handleCommand(String input) {
        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);

        Command command = commands.get(commandName);

        if (command == null) {
            return "Error: Unknown command '" + commandName + "'. Type 'help' for a list of available commands.";
        }

        return command.execute(args);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
