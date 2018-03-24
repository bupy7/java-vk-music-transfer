package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

import java.util.concurrent.atomic.AtomicInteger;

public class ProfileCommand extends AbstractCommand {
    private VkRepository vkRepository;

    public ProfileCommand(DefaultAbsSender absSender, VkRepository vkRepository) {
        super(absSender);

        this.vkRepository = vkRepository;
    }

    @Override
    public void execute(final Message input) {
        int profileId = fetchProfileId(input.getText());
        if (profileId == 0) {
            sendMessage(new SendMessage()
                    .setChatId(input.getChatId())
                    .setText("You must enter the profile ID to grab music"));

            return;
        }

        Message responseProgressMessage = sendMessage(new SendMessage()
                .setChatId(input.getChatId())
                .setText("Wait..."));
        EditMessageText progressMessage = new EditMessageText()
                .setMessageId(responseProgressMessage.getMessageId())
                .setChatId(responseProgressMessage.getChatId());

        vkRepository.findAllByProfile(profileId).thenAccept(action -> {
            if (action.size() == 0) {
                sendMessage(new SendMessage()
                        .setChatId(input.getChatId())
                        .setText("Nothing found"));
            } else {
                final int total = action.size();
                final AtomicInteger current = new AtomicInteger(0);

                action.forEach((name, link) -> {
                    progressMessage
                            .setText(getProgress(current.incrementAndGet(), total))
                            .enableMarkdown(true);
                    editMessageText(progressMessage);

                    SendAudio audio = new SendAudio()
                            .setChatId(input.getChatId())
                            .setAudio(link)
                            .setPerformer(name[0])
                            .setTitle(name[1])
                            .setCaption(String.join(" - ", name));
                    sendAudio(audio);
                });

                sendMessage(new SendMessage()
                        .setChatId(input.getChatId())
                        .setText("That is all!"));
            }
        });
    }

    private int fetchProfileId(String text) {
        String[] matches = text.split(" ", 3);
        if (matches.length < 2) {
            return 0;
        }
        return Integer.valueOf(matches[1]);
    }

    private String getProgress(int current, int total) {
        return String.format("*Progress:* %d/%d", current, total);
    }
}
