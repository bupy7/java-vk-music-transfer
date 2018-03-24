package ru.mihaly4.vkmusictransfer.client;

public interface IVkClient {
    String fromProfile(int id, int offset);
    String getRemixSid();
    int getUid();
}
