package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractGrabCommand extends AbstractCommand {
    public AbstractGrabCommand(DefaultAbsSender absSender) {
        super(absSender);
    }

    @Override
    public void execute(final Message input) {
        String id = fetchId(input.getText());
        if (id.length() == 0) {
            sendMessage(new SendMessage()
                    .setChatId(input.getChatId())
                    .setText(getValidateMessage()));

            return;
        }

        Message responseProgressMessage = sendMessage(new SendMessage()
                .setChatId(input.getChatId())
                .setText("Wait..."));

        pinMessage(responseProgressMessage);

        EditMessageText progressMessage = new EditMessageText()
                .setMessageId(responseProgressMessage.getMessageId())
                .setChatId(responseProgressMessage.getChatId());

        findAll(id).thenAccept(action -> {
            if (action.size() == 0) {
                sendMessage(new SendMessage()
                        .setChatId(input.getChatId())
                        .setText("Nothing found"));
            } else {
                final int total = action.size();
                final AtomicInteger current = new AtomicInteger(0);

                action.forEach((link, name) -> {
                    progressMessage
                            .setText(getProgress(current.incrementAndGet(), total))
                            .enableMarkdown(true);
                    editMessageText(progressMessage);

                    SendAudio audio = new SendAudio()
                            .setChatId(input.getChatId())
                            .setAudio(link)
                            // don't work if to send audio by URL
                            //.setPerformer(name[0])
                            //.setTitle(name[1])
                            .setCaption(String.join(" - ", name));
                    sendAudio(audio);
                });
            }
        });
    }

    protected abstract CompletableFuture<Map<String, String[]>> findAll(String id);

    protected abstract String getValidateMessage();

    private String fetchId(String text) {
        String[] matches = text.split(" ", 3);
        if (matches.length < 2) {
            return "";
        }
        return matches[1];
    }

    private String getProgress(int current, int total) {
        return String.format("*Progress:* %d/%d%s", current, total, current == total ? ". Done!" : "");
    }
}
