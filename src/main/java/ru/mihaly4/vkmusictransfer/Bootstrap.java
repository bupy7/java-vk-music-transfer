package ru.mihaly4.vkmusictransfer;

import ru.mihaly4.vkmusictransfer.helper.ArgsHelper;
import ru.mihaly4.vkmusictransfer.ui.Messager;
import org.apache.commons.cli.ParseException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bootstrap {
    private static final int EXIT_STATUS_ERROR = 1;

    private static Messager messager = new Messager();

    private Bootstrap(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Bot(parseArgs(args).getTgbUsername(), parseArgs(args).getTgbToken()));

            messager.println(">>> STARTED at " + getTimestamp());
        } catch (TelegramApiException e) {
            messager.println(">>> ERROR: \n-" + e.getMessage());

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
            messager.println(">>> ERROR: \n- " + e.getMessage());

            System.exit(EXIT_STATUS_ERROR);
        }

        return argsHelper;
    }

    private String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
