package org.goober.client.commands;


import org.goober.client.network.ServerConnection;

public class LogoutCommand implements Command{

    private ServerConnection serverConnection;

    public LogoutCommand(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    @Override
    public String execute(String[] args) {

        if (args.length > 0) {

            return "Invalid amount of arguments. " + this.getUsage();
        }

        return "Logout executed.";
    }

    @Override
    public String getDescription() {
        return "Logs out the user from the current session.";
    }

    @Override
    public String getUsage() {
        return "Usage: logout";
    }
}
