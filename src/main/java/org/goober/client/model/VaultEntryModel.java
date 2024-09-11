package org.goober.client.model;

import java.io.Serializable;

public class VaultEntryModel implements Serializable {

    String password;
    String website;
    String notes;

    private VaultEntryModel(){
    }

    private VaultEntryModel(String password, String website, String notes){
        this.password = password;
        this.website = website;
        this.notes = notes;
    }

    public static VaultEntryModel newVaultEntry(String password, String website, String notes){
        return new VaultEntryModel(password, website, notes);
    }

    public static VaultEntryModel newVaultEntry(){
        return new VaultEntryModel("example_password", "example_website", "example_notes");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
