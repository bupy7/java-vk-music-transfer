package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import ru.mihaly4.vkmusictransfer.ui.Messager;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public abstract class AbstractCommand {
    private DefaultAbsSender absSender;
    private Messager messager = new Messager();

    AbstractCommand(DefaultAbsSender absSender) {
        this.absSender = absSender;
    }

    public abstract void execute(final Message input);

    protected void sendMessage(SendMessage message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            messager.println("SEND MESSAGE ERROR: \n- " + e.getMessage());
        }
    }

    protected void sendAudio(SendAudio audio) {
        try {
            absSender.sendAudio(audio);
        } catch (TelegramApiException e) {
            messager.println("SEND AUDIO ERROR: \n- " + e.getMessage());
        }
    }
}
