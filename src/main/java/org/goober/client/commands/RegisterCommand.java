package org.goober.client.commands;

import org.goober.client.network.ServerConnection;
import org.goober.client.util.EncryptionUtil;
import org.goober.client.util.KeyUtil;
import org.goober.client.model.UserModel;
import org.goober.client.util.VaultUtil;

import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class RegisterCommand implements Command {

    private final ServerConnection serverConnection;
    private final UserModel userModel;

    public RegisterCommand(ServerConnection serverConnection, UserModel currentUser) {
        this.serverConnection = serverConnection;
        this.userModel = currentUser;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2) {
            return "Invalid number of arguments. " + getUsage();
        }

        String username = args[0];
        String password = args[1];

        String authSalt = KeyUtil.generateSalt();
        String vaultSalt = KeyUtil.generateSalt();
        String authKey = "";
        String vaultKey = "";
        String vault = "";
        byte[] iv;

        try{
            vaultKey = KeyUtil.deriveVaultKey(username, password, vaultSalt);
            authKey = KeyUtil.deriveAuthKey(vaultKey, password, authSalt);
            iv = EncryptionUtil.generateIV();
            vault = VaultUtil.getEncryptedVaultInstance(KeyUtil.deriveKey(username, password, vaultSalt).getEncoded(), iv);
        }

        catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during the registration process. Please try again.";
        }

        String request = "register " + username + " " + authKey + " " + authSalt + " " + Base64.getEncoder().encodeToString(iv) + " " + vaultSalt + " " + vault;
        String response = serverConnection.sendCommand(request);

        if (response.equalsIgnoreCase("1")){
            return "Registration successful! Welcome onboard " + username + "!";
        }
        try {
            userModel.setUsername(username);
            userModel.setIv(iv);
            userModel.setAuthSalt(Base64.getDecoder().decode(authSalt));
            userModel.setVaultSalt(Base64.getDecoder().decode(vaultSalt));
            userModel.setPasswordVault(VaultUtil.decryptPasswordVault(vault, Base64.getDecoder().decode(vaultKey), iv));
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public String getDescription() {
        return "Registers a new user with the specified username and password.";
    }

    @Override
    public String getUsage() {
        return "Usage: register <username> <password>";
    }
}

