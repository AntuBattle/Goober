package org.goober.client.commands;

import org.goober.client.network.ServerConnection;
import org.goober.client.util.HashingUtil;
import org.goober.client.model.UserModel;
import org.goober.client.util.KeyUtil;
import org.goober.client.util.VaultUtil;
import org.goober.server.database.Database;

import java.util.Base64;

public class LoginCommand implements Command {

    private final ServerConnection serverConnection;
    private final UserModel user;

    public LoginCommand(ServerConnection serverConnection, UserModel user) {
        this.serverConnection = serverConnection;
        this.user = user;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2) {
            return "Invalid number of arguments. " + getUsage();
        }

        String username = args[0];
        String password = args[1];

        String userInfo = serverConnection.sendCommand("get_user_info " + username);
        String[] userArgs = userInfo.trim().split("\\s+");
        String vaultSalt = userArgs[0];
        String authSalt = userArgs[1];
        String iv = userArgs[2];

        try{
            String vaultKey = KeyUtil.deriveVaultKey(username, password, vaultSalt);
            String authKey = KeyUtil.deriveAuthKey(vaultKey, password, authSalt);
            String encryptedVault = Database.retrieveEncryptedVault(authKey);
            byte[] byteVaultKey = Base64.getDecoder().decode(vaultKey);
            byte[] byteIv = Base64.getDecoder().decode(iv);
            user.setUsername(username);
            user.setPasswordVault(VaultUtil.decryptPasswordVault(encryptedVault, byteVaultKey, byteIv));
            user.setIv(byteIv);
            user.setVaultSalt(Base64.getDecoder().decode(vaultSalt));
            user.setAuthSalt(Base64.getDecoder().decode(authSalt));

            return "Login successful! Welcome back " + username + "!";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Something went wrong in the login process. Please check your password and try again.";
        }
    }

    @Override
    public String getDescription() {
        return "Logs in a user with the specified username and password.";
    }

    @Override
    public String getUsage() {
        return "Usage: login <username> <password>";
    }
}
