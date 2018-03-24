package ru.mihaly4.vkmusictransfer;

import org.telegram.telegrambots.api.objects.Message;
import ru.mihaly4.vkmusictransfer.command.AbstractCommand;
import ru.mihaly4.vkmusictransfer.command.ProfileCommand;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private static final String COMMAND_PROFILE = "profile";
    private static final String WORD_SEPARATOR = " ";
    private static final String COMMAND_SEPARATOR = "@";

    private String username = "";
    private String token = "";
    private VkRepository vkRepository;

    private Map<String, AbstractCommand> commands = new HashMap<>();

    public Bot(String username, String token, VkRepository vkRepository) {
        this.username = username;
        this.token = token;
        this.vkRepository = vkRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = null;
        if (update.hasChannelPost()) {
            message = update.getChannelPost();
        }
        if (update.hasMessage()) {
            message = update.getMessage();
        }

        if (message != null) {
            String[] input = message.getText().trim().split(WORD_SEPARATOR, 2);

            if (input.length != 0) {
                String name = input[0].split(COMMAND_SEPARATOR, 2)[0].toLowerCase().substring(1);

                AbstractCommand command = getCommand(name);
                if (command != null) {
                    command.execute(message);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Nullable
    private synchronized AbstractCommand getCommand(String name) {
        if (!commands.containsKey(name)) {
            switch (name) {
                case COMMAND_PROFILE:
                    commands.put(COMMAND_PROFILE, new ProfileCommand(this, vkRepository));
                    break;
            }
        }

        return commands.get(name);
    }
}
