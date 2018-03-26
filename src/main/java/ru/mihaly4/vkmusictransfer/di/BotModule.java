package ru.mihaly4.vkmusictransfer.di;

import dagger.Module;
import dagger.Provides;
import ru.mihaly4.vkmusictransfer.Bot;
import ru.mihaly4.vkmusictransfer.config.PackageConfig;
import ru.mihaly4.vkmusictransfer.client.VkClient;
import ru.mihaly4.vkmusictransfer.decoder.VkMusicLinkDecoder;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

@Module
public class BotModule {
    private PackageConfig config;

    public BotModule(PackageConfig config) {
        this.config = config;
    }

    @Provides
    @SharedScope
    public VkClient provideVkClient() {
        return new VkClient(config.getVkRemixSid(), config.getVkUid());
    }

    @Provides
    @SharedScope
    public VkMusicLinkDecoder provideVkMusicLinkDecoder() {
        return new VkMusicLinkDecoder();
    }

    @Provides
    @SharedScope
    public VkRepository prodideVkRepository(VkClient vkClient, VkMusicLinkDecoder vkMusicLinkDecoder) {
        return new VkRepository(vkClient, vkMusicLinkDecoder);
    }

    @Provides
    public Bot provideBot(VkRepository vkRepository) {
        return new Bot(config.getTgbUsername(), config.getTgbToken(), vkRepository);
    }
}
