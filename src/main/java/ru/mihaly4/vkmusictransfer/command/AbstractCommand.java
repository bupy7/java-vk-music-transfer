package ru.mihaly4.vkmusictransfer.command;

import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.api.methods.pinnedmessages.UnpinChatMessage;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import ru.mihaly4.vkmusictransfer.log.ConsoleLog;
import ru.mihaly4.vkmusictransfer.log.ILog;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public abstract class AbstractCommand {
    private DefaultAbsSender absSender;
    private ILog log = new ConsoleLog();

    AbstractCommand(DefaultAbsSender absSender) {
        this.absSender = absSender;
    }

    public abstract void execute(final Message input);

    @Nullable
    protected Message sendMessage(SendMessage message) {
        try {
            return absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("SEND MESSAGE: \n- " + e.getMessage());
        }
        return null;
    }

    protected void editMessageText(EditMessageText message) {
        try {
             absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("SEND MESSAGE: \n- " + e.getMessage());
        }
    }

    protected void sendAudio(SendAudio audio) {
        try {
            absSender.sendAudio(audio);
        } catch (TelegramApiException e) {
            log.error("SEND AUDIO: \n- " + e.getMessage());
        }
    }

    protected void pinMessage(Message message) {
        if (message.getChat().isUserChat()) {
            return;
        }
        PinChatMessage pinChatMessage = new PinChatMessage();
        pinChatMessage.setChatId(message.getChatId())
                .setMessageId(message.getMessageId())
                .setDisableNotification(true);
        try {
            absSender.execute(pinChatMessage);
        } catch (TelegramApiException e) {
            log.error("PIN MESSAGE: \n- " + e.getMessage());
        }
    }

    /**
     * @since 1.1.0
     */
    protected void unpinMessage(Message message) {
        if (message.getChat().isUserChat()) {
            return;
        }
        UnpinChatMessage unpinChatMessage = new UnpinChatMessage();
        unpinChatMessage.setChatId(message.getChatId());
        try {
            absSender.execute(unpinChatMessage);
        } catch (TelegramApiException e) {
            log.error("UNPIN MESSAGE: \n- " + e.getMessage());
        }
    }
}
