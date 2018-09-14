package ru.mihaly4.vkmusictransfer;

import org.telegram.telegrambots.api.objects.Message;
import ru.mihaly4.vkmusictransfer.client.IVkClient;
import ru.mihaly4.vkmusictransfer.command.AbstractCommand;
import ru.mihaly4.vkmusictransfer.command.LoginCommand;
import ru.mihaly4.vkmusictransfer.command.WallCommand;
import ru.mihaly4.vkmusictransfer.command.AudioCommand;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.mihaly4.vkmusictransfer.log.ConsoleLog;
import ru.mihaly4.vkmusictransfer.log.ILog;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private static final String COMMAND_AUDIO = "audio";
    private static final String COMMAND_WALL = "wall";
    private static final String COMMAND_LOGIN = "login";
    private static final String WORD_SEPARATOR = " ";
    private static final String COMMAND_SEPARATOR = "@";

    private String username;
    private String token;
    private VkRepository vkRepository;
    private List<String> trustedUsers;
    private ILog log = new ConsoleLog();
    private IVkClient vkClient;

    private Map<String, AbstractCommand> commands = new HashMap<>();

    public Bot(
            String username,
            String token,
            VkRepository vkRepository,
            List<String> trustedUsers,
            IVkClient vkClient
    ) {
        this.username = username;
        this.token = token;
        this.vkRepository = vkRepository;
        this.trustedUsers = trustedUsers;
        this.vkClient = vkClient;
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
            if (isTrusted(message.getChat().getUserName())) {
                processMessage(message);
            } else {
                log.error("Access denied for " + message.getFrom().toString());
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
                case COMMAND_AUDIO:
                    commands.put(COMMAND_AUDIO, new AudioCommand(this, vkRepository));
                    break;

                case COMMAND_WALL:
                    commands.put(COMMAND_WALL, new WallCommand(this, vkRepository));
                    break;

                case COMMAND_LOGIN:
                    commands.put(COMMAND_LOGIN, new LoginCommand(this, vkClient));
                    break;
            }
        }

        return commands.get(name);
    }

    private void processMessage(Message message) {
        String[] input = new String[0];
        if (message.getText() != null) {
            input = message.getText().trim().split(WORD_SEPARATOR, 2);
        }

        if (input.length != 0) {
            String name = input[0].split(COMMAND_SEPARATOR, 2)[0].toLowerCase().substring(1);

            AbstractCommand command = getCommand(name);
            if (command != null) {
                command.execute(message);
            }
        }
    }

    private boolean isTrusted(@Nullable String username) {
        if (trustedUsers.size() == 0) {
            return true;
        }
        return trustedUsers.contains(username);
    }
}
