package org.goober.server.commands;

import org.goober.server.database.Database;

public class GetUserInfoServerCommand implements Command{
    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String execute(String[] args) {


        try {
            String username = args[0];
            String authSalt = Database.getUserAuthSalt(username);
            String vaultSalt = Database.getUserVaultSalt(username);
            String iv = Database.getUserVaultIv(username);

            return vaultSalt + " " + authSalt + " " + iv;

        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong while retrieving data. Please check your password and server status and try again.";
        }
    }
}
