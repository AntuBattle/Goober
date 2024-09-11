package org.goober.client.util;

import org.goober.client.model.PasswordVaultModel;
import org.goober.client.model.VaultEntryModel;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.HashMap;

public class VaultUtil {

    private VaultUtil(){}


    public static String getEncryptedVaultInstance(byte[] vaultKey, byte[] iv) throws Exception{

        PasswordVaultModel  newVault = getNewVaultInstance();
        return encryptPasswordVault(newVault, vaultKey, iv);
    }


    private static PasswordVaultModel getNewVaultInstance(){

        return new PasswordVaultModel();
    }

    public static String encryptPasswordVault(PasswordVaultModel vault, byte[] vaultKey, byte[] iv) throws Exception{
        String serializedVault = SerializationUtil.serialize(vault);
        return EncryptionUtil.encrypt(serializedVault, vaultKey, new IvParameterSpec(iv));
    }

    public static PasswordVaultModel decryptPasswordVault(String encryptedPasswordVault, byte[] vaultKey, byte[] iv) throws Exception{
        String serializedDecryptedVault = EncryptionUtil.decrypt(encryptedPasswordVault, vaultKey, new IvParameterSpec(iv));
        return (PasswordVaultModel) SerializationUtil.deserialize(serializedDecryptedVault);
    }
}
