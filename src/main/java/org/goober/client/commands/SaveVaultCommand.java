package org.goober.client.commands;

import org.goober.client.model.PasswordVaultModel;
import org.goober.client.model.UserModel;
import org.goober.client.network.ServerConnection;
import org.goober.client.util.KeyUtil;
import org.goober.client.util.VaultUtil;

import java.util.Base64;

public class SaveVaultCommand implements Command{

    private final ServerConnection serverConnection;
    private final UserModel user;

    public SaveVaultCommand(ServerConnection serverConnection, UserModel user){
        this.serverConnection = serverConnection;
        this.user = user;
    }

    @Override
    public String execute(String[] args) {

        if (args.length != 1) {
            return "Invalid number of arguments. Usage: " + getUsage();
        }

        String password = args[0];
        PasswordVaultModel vault = user.getPasswordVault();
        try {
            String encryptedVault = VaultUtil.encryptPasswordVault(user.getPasswordVault(), Base64.getDecoder().decode(KeyUtil.deriveVaultKey(user.getUsername(), password, Base64.getEncoder().encodeToString(user.getVaultSalt()))), user.getIv());
            String vaultKey = KeyUtil.deriveVaultKey(user.getUsername(), password, Base64.getEncoder().encodeToString(user.getVaultSalt()));
            String authKey = KeyUtil.deriveAuthKey(vaultKey, password, Base64.getEncoder().encodeToString(user.getAuthSalt()));
            String response = serverConnection.sendCommand("save_vault " + encryptedVault + " " + authKey);
            return response;
        }

        catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong. try again.";
        }
    }

    @Override
    public String getDescription() {
        return "Saves the vault to the server, confirming the changes made locally.";
    }

    @Override
    public String getUsage() {
        return "save_vault <password>";
    }
}
