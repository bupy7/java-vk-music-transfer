package ru.mihaly4.vkmusictransfer.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class VkClient implements VkClientInterface {
    private static final String BASE_PROFILE_URL = "https://m.vk.com/audios";

    private String remixSid = "";
    private int uid = 0;
    private OkHttpClient httpClient;

    public VkClient(String remixSid, int uid) {
        this.remixSid = remixSid;
        this.uid = uid;
    }

    @Override
    public String fromProfile(int id, int offset) {
        Request request = new Request.Builder()
                .url(BASE_PROFILE_URL + id + "?offset=" + offset)
                .addHeader("Cookie", "remixsid=" + remixSid)
                .get()
                .build();
        Response response;
        String html = "";
        try {
            response = getHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                html = response.body().string();
            }
        } catch (IOException | NullPointerException e) {
            html = "";
        }

        return html;
    }

    @Override
    public String getRemixSid() {
        return remixSid;
    }

    @Override
    public int getUid() {
        return uid;
    }

    private synchronized OkHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient()
                    .newBuilder()
                    .followRedirects(false)
                    .followSslRedirects(false)
                    .build();
        }
        return httpClient;
    }
}
