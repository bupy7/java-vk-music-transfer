package ru.mihaly4.vkmusictransfer.config;

import java.util.List;

public class PackageConfig {
    private String tgbToken = "";
    private String tgbUsername = "";
    private List<String> trustedTgbUsers;

    public String getTgbToken() {
        return tgbToken;
    }

    public void setTgbToken(String tgbToken) {
        this.tgbToken = tgbToken;
    }

    public String getTgbUsername() {
        return tgbUsername;
    }

    public void setTgbUsername(String tgbUsername) {
        this.tgbUsername = tgbUsername;
    }

    public List<String> getTrustedTgbUsers() {
        return trustedTgbUsers;
    }

    public void setTrustedTgbUsers(List<String> trustedTgbUsers) {
        this.trustedTgbUsers = trustedTgbUsers;
    }
}
