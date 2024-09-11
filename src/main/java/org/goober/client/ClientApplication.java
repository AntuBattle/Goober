package org.goober.client;

import org.goober.client.model.UserModel;
import org.goober.client.network.ServerConnection;
import org.goober.client.util.EncryptionUtil;
import org.goober.client.util.KeyUtil;
import org.goober.client.util.VaultUtil;

import java.util.Base64;
import java.util.Scanner;

public class ClientApplication {

    public static void main(String[] args) {
        // Create a server connection
        ServerConnection serverConnection = new ServerConnection("localhost", 9090);

        if (!serverConnection.connect()) {
            System.out.println("Failed to connect to the server. Exiting...");
            return;
        }

        //Create the user model
        UserModel user = new UserModel();

        // Create the command handler
        CommandHandler commandHandler = new CommandHandler(serverConnection, user);

        // Start the command line interface
        Scanner scanner = new Scanner(System.in);
        String commandInput;

        System.out.println("Welcome to Goober! Type 'help' for a list of commands.");

        while (true) {
            System.out.print("cli> ");
            commandInput = scanner.nextLine().trim();

            String response = commandHandler.handleCommand(commandInput);

            System.out.println(response);

            if (response.equalsIgnoreCase("Logout executed.")) break;
        }

        System.out.println("To confirm and save vault changes, please type in your password: ");
        String password = scanner.nextLine().trim();
        try{
            String response = commandHandler.handleCommand("save_vault" + " " + password);
            System.out.println(response);
        }

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("NOTE: The vault might not have been saved correctly due to a server error.");
        }


        serverConnection.disconnect();
        System.out.println("Goodbye!");
    }
}