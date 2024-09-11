package org.goober.server.model;


public class PasswordVaultServerModel {

    private String passwordVault;
    private byte[] authSalt;
    private byte[] iv;


    public PasswordVaultServerModel(String passwordVault, byte[] authSalt, byte[] iv) {
        this.passwordVault = passwordVault;
        this.authSalt = authSalt;
        this.iv = iv;
    }
}

