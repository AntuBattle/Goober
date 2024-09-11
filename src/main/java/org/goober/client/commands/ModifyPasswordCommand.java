package org.goober.client.commands;

import org.goober.client.model.UserModel;
import org.goober.client.model.VaultEntryModel;
import org.goober.client.util.ArrayToStringUtil;

public class ModifyPasswordCommand implements Command{

    UserModel user;

    public ModifyPasswordCommand(UserModel user) {
        this.user = user;
    }

    @Override
    public String execute(String[] args) {

        if (args.length < 2) {
            return "Incorrect number of arguments. Usage: " + getUsage();
        }


        String newLabel = args[0];
        String newPassword = args[1];

        if (args.length == 3) {
            String website = args[2];
            user.getPasswordVault().modifyEntry(newLabel, newPassword, website);
        }

        else if (args.length > 3) {
            String website = args[2];
            String notes = ArrayToStringUtil.mergeArrayElements(args, 3);
            user.getPasswordVault().modifyEntry(newLabel, newPassword, website, notes);
        }

        else {
            user.getPasswordVault().modifyEntry(newLabel, newPassword);
        }

        return "Entry modified successfully.";
    }

    @Override
    public String getDescription() {
        return "Modify vault entries using this command";
    }

    @Override
    public String getUsage() {
        return "modify_password <label> <new_password> [new_website] [new_notes].";
    }
}
