package ru.mihaly4.vkmusictransfer.config;

import ru.mihaly4.vkmusictransfer.helper.ArgsHelper;

public class PackageConfigFactory {
    public static PackageConfig createFromArgs(ArgsHelper argsHelper) {
        PackageConfig config = new PackageConfig();

        config.setTgbToken(argsHelper.getTgbToken());
        config.setTgbUsername(argsHelper.getTgbUsername());
        config.setTrustedTgbUsers(argsHelper.getTrustedTgbUsers());

        return config;
    }
}
