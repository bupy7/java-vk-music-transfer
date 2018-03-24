package ru.mihaly4.vkmusictransfer;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String[] input = update.getMessage()
                    .getText()
                    .trim()
                    .split(" ", 2);

            if (input.length != 0) {
                String name = input[0].toLowerCase().substring(1);

                AbstractCommand command = getCommand(name);
                if (command != null) {
                    command.execute(update);
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
    private AbstractCommand getCommand(String name) {
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
