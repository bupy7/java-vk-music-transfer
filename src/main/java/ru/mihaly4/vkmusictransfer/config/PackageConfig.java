package ru.mihaly4.vkmusictransfer.config;

import java.util.List;

public class PackageConfig {
    private String tgbToken = "";
    private String tgbUsername = "";
    private String vkRemixSid = "";
    private int vkUid = 0;
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

    public String getVkRemixSid() {
        return vkRemixSid;
    }

    public void setVkRemixSid(String vkRemixSid) {
        this.vkRemixSid = vkRemixSid;
    }

    public int getVkUid() {
        return vkUid;
    }

    public void setVkUid(int vkUid) {
        this.vkUid = vkUid;
    }

    public List<String> getTrustedTgbUsers() {
        return trustedTgbUsers;
    }

    public void setTrustedTgbUsers(List<String> trustedTgbUsers) {
        this.trustedTgbUsers = trustedTgbUsers;
    }
}
