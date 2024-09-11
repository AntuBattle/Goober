package org.goober.server.commands;

import org.goober.server.database.Database;

import java.net.Socket;
import java.sql.SQLIntegrityConstraintViolationException;


public class RegisterServerCommand implements Command{

    Socket clientSocket;

    public RegisterServerCommand(Socket clientSocket){

        this.clientSocket = clientSocket;
    }


    /**
     * Executes the register command
     *
     * @param args In order the expected args are: username, derived_auth_key and salt.
     * @return returns a string with the status of the operation, telling if it was successful.
     */
    @Override
    public String execute(String[] args) {

        String username = args[0];
        String authKey = args[1];
        String authSalt = args[2];
        String iv = args[3];
        String vaultSalt = args[4];
        String vault = args[5];


        int result = 0;

        try {
            result = Database.registerUser(username, authKey, authSalt, vaultSalt, iv, vault);
        }

        catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            return "Error: User already exists!";
        }

        catch (Exception e){
            e.printStackTrace();
            return "Unkown error. Try again";
        }

        if (result == 0) {
            return "Something unexpected happened during the registration process. Please try again.";
        }
        else if (result == 1) {
            return "Registered successfully. Welcome! Type 'help' to view the available commands.";
        }
        else {
            return "The return code from the database was unexpected. Please try again.";
        }

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
