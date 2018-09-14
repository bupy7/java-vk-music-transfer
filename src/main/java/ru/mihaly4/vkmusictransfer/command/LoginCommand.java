package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import ru.mihaly4.vkmusictransfer.client.IVkClient;
import java.lang.Thread;

/**
 * @since 1.1.0
 */
public class LoginCommand extends AbstractCommand {
    private IVkClient vkClient;

    public LoginCommand(DefaultAbsSender absSender, IVkClient vkClient) {
        super(absSender);
        this.vkClient = vkClient;
    }

    @Override
    public void execute(Message input) {
        String phone = fetchPhone(input.getText());
        String password = fetchPassword(input.getText());

        if (phone.length() == 0 || password.length() == 0) {
            sendMessage(new SendMessage()
                    .setChatId(input.getChatId())
                    .setText("You must enter the phone and password"));
            return;
        }

        sendMessage(new SendMessage()
                .setChatId(input.getChatId())
                .setText("Wait..."));

        new Thread(() -> {
            if (vkClient.login(phone, password)) {
                sendMessage(new SendMessage()
                        .setChatId(input.getChatId())
                        .setText("You have logged successfully!"));
            } else {
                sendMessage(new SendMessage()
                        .setChatId(input.getChatId())
                        .setText("Invalid phone or password"));
            }
        }).run();
    }

    private String fetchPhone(String text) {
        String[] matches = text.split(" ", 3);
        if (matches.length < 3) {
            return "";
        }
        return matches[1];
    }

    private String fetchPassword(String text) {
        String[] matches = text.split(" ", 3);
        if (matches.length < 3) {
            return "";
        }
        return matches[2];
    }
}
