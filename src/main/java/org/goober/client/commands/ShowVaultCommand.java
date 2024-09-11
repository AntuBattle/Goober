package org.goober.client.commands;

import org.goober.client.model.UserModel;
import org.goober.client.model.VaultEntryModel;

import java.util.HashMap;
import java.util.Map;

public class ShowVaultCommand implements Command{

    UserModel user;

    public ShowVaultCommand(UserModel user){
        this.user = user;
    }
    @Override
    public String execute(String[] args) {

        HashMap<String, VaultEntryModel> entries = user.getPasswordVault().getInfo();
        for(Map.Entry<String, VaultEntryModel> entry : entries.entrySet()){
            System.out.println("\nLABEL: " + entry.getKey() + "\n|\n|-->\t" + " PASSWORD: " + entry.getValue().getPassword() + "\t\tWEBSITE: " + entry.getValue().getWebsite() + "\tNOTES: " + entry.getValue().getNotes()+ "\n");
        }

        return "That's it! Your whole vault. Thanks for using Goober!";
    }

    @Override
    public String getUsage() {
        return "show_vault";
    }

    @Override
    public String getDescription() {
        return "Shows all the contents of the password vault";
    }
}
