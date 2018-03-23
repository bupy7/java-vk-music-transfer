package ru.mihaly4.vkmusictransfer.client;

import java.util.concurrent.CompletableFuture;

public interface VkClientInterface {
    CompletableFuture<String> fromProfile(int id, int offset);
    String getRemixSid();
    int getUid();
}
