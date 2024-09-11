package org.goober.client.commands;

import org.goober.client.model.UserModel;

import java.util.Scanner;

public class DeletePasswordCommand implements Command{

    UserModel user;

    public DeletePasswordCommand(UserModel user) {
        this.user = user;
    }

    @Override
    public String execute(String[] args) {

        if (args.length != 1) return "Incorrect number of arguments. Usage: " + getUsage();

        System.out.println("Are you sure you want to remove " + args[0] + "? [Y/n]");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if (answer.trim().equalsIgnoreCase("y")) {
            if(user.getPasswordVault().getEntry(args[0]) != null){
                user.getPasswordVault().deleteEntry(args[0]);
                return "Entry deleted successfully.";
            }
            else return "Entry not found in the vault. Please try again.";

        }

        else {
            return "Got it! Entry has not been deleted.";
        }
    }

    @Override
    public String getDescription() {
        return "Delete vault entries";
    }

    @Override
    public String getUsage() {
        return "delete_password <label>" ;
    }
}
