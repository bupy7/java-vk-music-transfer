package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultAbsSender;

public class ProfileCommand extends AbstractCommand {
    public ProfileCommand(DefaultAbsSender absSender) {
        super(absSender);
    }

    @Override
    public void execute(Update update) {
        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(update.getMessage().getText());

        send(message);
    }
}
