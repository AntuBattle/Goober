package org.goober.server.commands;

import org.goober.server.database.Database;

public class SaveVaultServerCommand implements Command{

    @Override
    public String execute(String[] args) {

        String vault = args[0];
        String authKey = args[1];

        try {
            Database.storeEncryptedVault(authKey, vault);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "Vault saved successfully! Thanks for using Goober!";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getUsage() {
        return "";
    }

}
