package org.goober.client.model;

public class UserModel {

    private String username;
    private byte[] iv;
    private byte[] vaultSalt;
    private byte[] authSalt;
    private PasswordVaultModel passwordVault;

    public UserModel() {
        this.username = "";
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public byte[] getVaultSalt() {
        return vaultSalt;
    }

    public void setVaultSalt(byte[] vaultSalt) {
        this.vaultSalt = vaultSalt;
    }

    public byte[] getAuthSalt() {
        return authSalt;
    }

    public void setAuthSalt(byte[] authSalt) {
        this.authSalt = authSalt;
    }

    public PasswordVaultModel getPasswordVault() {
        return passwordVault;
    }

    public void setPasswordVault(PasswordVaultModel passwordVault) {
        this.passwordVault = passwordVault;
    }
}

