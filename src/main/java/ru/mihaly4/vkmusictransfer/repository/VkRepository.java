package ru.mihaly4.vkmusictransfer.repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.mihaly4.vkmusictransfer.client.VkClientInterface;
import ru.mihaly4.vkmusictransfer.decoder.VkMusicLinkDecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class VkRepository {
    private static final int STEP = 100;

    private VkClientInterface client;
    private VkMusicLinkDecoder linkDecoder;

    VkRepository(VkClientInterface client, VkMusicLinkDecoder linkDecoder) {
        this.client = client;
        this.linkDecoder = linkDecoder;
    }

    public CompletableFuture<Map<String, String>> findAllByProfile(int id) {
        return CompletableFuture.supplyAsync(() -> {
            final Map<String, String> links = new HashMap<>();
            int oldLinkSize = 0;
            int page = 0;

            do {
                oldLinkSize = links.size();

                client.fromProfile(id, STEP * page++).thenAccept(action -> {
                    if (action.length() == 0) {
                        return;
                    }

                    Document doc = Jsoup.parse(action);

                    Elements tracks = doc.select(".audio_item .ai_body");

                    for (int i = 0; i != tracks.size(); i++) {
                        Element track = tracks.get(i);

                        String title = track.selectFirst(".ai_artist").text();
                        title += " - " + track.selectFirst(".ai_title").text();

                        String encodedLink = track.selectFirst("input").val();
                        String link = linkDecoder.decode(encodedLink, client.getUid());

                        links.put(title, link);
                    }
                }).join();
            } while (oldLinkSize != links.size());

            return links;
        });
    }
}
