package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

import java.net.URL;

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
            SendMessage message = new SendMessage()
                    .setChatId(input.getChatId())
                    .setText("You must enter the profile ID to grab music");
            sendMessage(message);
        }

        vkRepository.findAllByProfile(profileId).thenAccept(action -> {
            if (action.size() == 0) {
                SendMessage message = new SendMessage()
                        .setChatId(input.getChatId())
                        .setText("Nothing found");
                sendMessage(message);
            } else {
                action.forEach((name, link) -> {
                    SendAudio audio = new SendAudio()
                            .setChatId(input.getChatId())
                            .setAudio(link)
                            .setPerformer(name[0])
                            .setTitle(name[1])
                            .setCaption(String.join(" - ", name));
                    sendAudio(audio);
                });
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
}
