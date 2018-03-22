package ru.mihaly4.vkmusictransfer.command;

import ru.mihaly4.vkmusictransfer.ui.Messager;
import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public abstract class AbstractCommand {
    private DefaultAbsSender absSender;
    private Messager messager = new Messager();

    AbstractCommand(DefaultAbsSender absSender) {
        this.absSender = absSender;
    }

    public abstract void execute(Update update);

    protected void send(BotApiMethod<? extends BotApiObject> method) {
        try {
            absSender.execute(method);
        } catch (TelegramApiException e) {
            messager.println("CMD ERROR: \n- " + e.getMessage());
        }
    }
}
