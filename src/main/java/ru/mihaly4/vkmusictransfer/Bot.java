package ru.mihaly4.vkmusictransfer;

import org.telegram.telegrambots.api.objects.Message;
import ru.mihaly4.vkmusictransfer.command.AbstractCommand;
import ru.mihaly4.vkmusictransfer.command.CommunityCommand;
import ru.mihaly4.vkmusictransfer.command.ProfileCommand;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.mihaly4.vkmusictransfer.log.ConsoleLog;
import ru.mihaly4.vkmusictransfer.log.ILog;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private static final String COMMAND_PROFILE = "profile";
    private static final String COMMAND_COMMUNITY = "com";
    private static final String WORD_SEPARATOR = " ";
    private static final String COMMAND_SEPARATOR = "@";

    private String username;
    private String token;
    private VkRepository vkRepository;
    private List<String> trustedUsers;
    private ILog log = new ConsoleLog();

    private Map<String, AbstractCommand> commands = new HashMap<>();

    @Inject
    public Bot(
            String username,
            String token,
            VkRepository vkRepository,
            List<String> trustedUsers
    ) {
        this.username = username;
        this.token = token;
        this.vkRepository = vkRepository;
        this.trustedUsers = trustedUsers;
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
                case COMMAND_PROFILE:
                    commands.put(COMMAND_PROFILE, new ProfileCommand(this, vkRepository));
                    break;

                case COMMAND_COMMUNITY:
                    commands.put(COMMAND_COMMUNITY, new CommunityCommand(this, vkRepository));
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
