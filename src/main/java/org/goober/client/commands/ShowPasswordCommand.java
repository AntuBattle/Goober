package org.goober.client.commands;

import org.goober.client.model.UserModel;
import org.goober.client.model.VaultEntryModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShowPasswordCommand implements Command{

    UserModel user;

    public ShowPasswordCommand(UserModel user) {
        this.user = user;
    }

    @Override
    public String execute(String[] args) {

        if(args.length != 1){
            return "Incorrect number of arguments. Usage: " + getUsage();
        }

        String label = args[0];
        VaultEntryModel entry = user.getPasswordVault().getEntry(label);

        System.out.println("|-->\t" + " PASSWORD: " + entry.getPassword() + "\t\tWEBSITE: " + entry.getWebsite() + "\tNOTES: " + entry.getNotes()+ "\n");
        return "Here is your vault entry!";
    }

    @Override
    public String getDescription() {
        return "Show single entries from your vault.";
    }

    @Override
    public String getUsage() {
        return "show_password <label>";
    }
}
