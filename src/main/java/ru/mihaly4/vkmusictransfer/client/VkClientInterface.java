package ru.mihaly4.vkmusictransfer.client;

public interface VkClientInterface {
    String fromProfile(int id, int offset);
    String getRemixSid();
    int getUid();
}
