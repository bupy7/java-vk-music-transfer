package ru.mihaly4.vkmusictransfer.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class VkClient implements VkClientInterface {
    private static final String BASE_PROFILE_URL = "https://m.vk.com/audios";

    private String remixSid = "";
    private int uid = 0;
    private OkHttpClient httpClient = new OkHttpClient();

    public VkClient(String remixSid, int uid) {
        this.remixSid = remixSid;
        this.uid = uid;
    }

    @Override
    public CompletableFuture<String> fromProfile(int id, int offset) {
        return CompletableFuture.supplyAsync(() -> {
            Request request = new Request.Builder()
                    .url(BASE_PROFILE_URL + id + "?offset=" + offset)
                    .addHeader("Cookie", "remixsid=" + remixSid)
                    .get()
                    .build();
            Response response;
            String html = "";
            try {
                response = httpClient.newCall(request).execute();
                if (response.isSuccessful() && response.body().string().length() == 0) {
                    html = response.body().string();
                }
            } catch (IOException e) {
                html = "";
            }

            return html;
        });
    }

    @Override
    public String getRemixSid() {
        return remixSid;
    }

    @Override
    public int getUid() {
        return uid;
    }
}
