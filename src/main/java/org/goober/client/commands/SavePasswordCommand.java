package org.goober.client.commands;

import org.goober.client.model.PasswordVaultModel;
import org.goober.client.model.UserModel;
import org.goober.client.network.ServerConnection;
import org.goober.client.util.ArrayToStringUtil;

public class SavePasswordCommand implements Command {

    private final UserModel user;

    public SavePasswordCommand(UserModel user) {
        this.user = user;
    }

    @Override
    public String execute(String[] args) {

        String label;
        String password;
        String website;

        if (args.length < 3) {
            return "Invalid number of arguments. " + getUsage();
        }

        label = args[0];
        password = args[1];
        website = args[2];
        PasswordVaultModel vault = user.getPasswordVault();

        if (args.length == 3) {
            vault.addEntry(label, password, website);
        }

        else {
            String notes = ArrayToStringUtil.mergeArrayElements(args, 3);
            vault.addEntry(label, password, website, notes);
        }


        return "Password saved successfully! To view it, just type 'show_vault'.";
    }

    @Override
    public String getDescription() {
        return "Saves a new password for the user on the server.";
    }

    @Override
    public String getUsage() {
        return "Usage: save_password <label> <password> <website> [notes]";
    }
}

