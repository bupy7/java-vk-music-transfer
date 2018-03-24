package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

public class ProfileCommand extends AbstractCommand {
    private VkRepository vkRepository;

    public ProfileCommand(DefaultAbsSender absSender, VkRepository vkRepository) {
        super(absSender);

        this.vkRepository = vkRepository;
    }

    @Override
    public void execute(Update update) {
        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(String.valueOf(fetchProfileId(update.getMessage().getText())));

        send(message);
    }

    private int fetchProfileId(String text) {
        String[] matches = text.split(" ", 3);
        if (matches.length < 2) {
            return 0;
        }
        return Integer.valueOf(matches[1]);
    }
}
