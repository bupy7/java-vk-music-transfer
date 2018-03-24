package ru.mihaly4.vkmusictransfer;

import ru.mihaly4.vkmusictransfer.client.VkClient;
import ru.mihaly4.vkmusictransfer.decoder.VkMusicLinkDecoder;
import ru.mihaly4.vkmusictransfer.helper.ArgsHelper;
import ru.mihaly4.vkmusictransfer.log.ConsoleLog;
import ru.mihaly4.vkmusictransfer.log.ILog;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;
import org.apache.commons.cli.ParseException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bootstrap {
    private static final int EXIT_STATUS_ERROR = 1;

    private static ILog log = new ConsoleLog();

    private Bootstrap(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        ArgsHelper conf = parseArgs(args);

        Bot bot = new Bot(
                conf.getTgbUsername(),
                conf.getTgbToken(),
                new VkRepository(
                        new VkClient(conf.getVkRemixSid(), conf.getVkUid()),
                        new VkMusicLinkDecoder()
                )
        );

        try {
            botsApi.registerBot(bot);

            log.info("STARTED");
        } catch (TelegramApiException e) {
            log.error("API EXCEPTION: \n-" + e.getMessage());

            System.exit(EXIT_STATUS_ERROR);
        }
    }

    public static void main(String[] args) {
        new Bootstrap(args);
    }

    private ArgsHelper parseArgs(String[] args) {
        ArgsHelper argsHelper = null;

        try {
            argsHelper = new ArgsHelper(args);
        } catch (ParseException e) {
            log.error("ERROR: \n- " + e.getMessage());

            System.exit(EXIT_STATUS_ERROR);
        }

        return argsHelper;
    }
}
