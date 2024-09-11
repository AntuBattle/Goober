package org.goober.client.model;

import java.io.Serializable;
import java.util.HashMap;

public class PasswordVaultModel implements Serializable {

    HashMap<String, VaultEntryModel> entries;

    public PasswordVaultModel() {
        this.entries = new HashMap<>();
    }

    public PasswordVaultModel(HashMap<String, VaultEntryModel> entries) {
        this.entries = entries;
    }

    public void addEntry(String label, String password, String website){
        entries.put(label, VaultEntryModel.newVaultEntry(password, website, ""));
    }

    public void addEntry(String label, String password, String website, String notes){
        entries.put(label, VaultEntryModel.newVaultEntry(password, website, notes));
    }

    public void modifyEntry(String label, String newPassword){
        entries.get(label).setPassword(newPassword);
    }

    public void modifyEntry(String label, String newPassword, String website){
        modifyEntry(label, newPassword);
        entries.get(label).setWebsite(website);
    }

    public void modifyEntry(String label, String newPassword, String website, String notes){
        modifyEntry(label, newPassword, website);
        entries.get(label).setNotes(notes);
    }

    public void deleteEntry(String label) {
        entries.remove(label);
    }

    public VaultEntryModel getEntry(String label) {
        return entries.get(label);
    }

    public HashMap<String, VaultEntryModel> getInfo(){
        return entries;
    }
}
