package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CommunityCommand extends AbstractGrabCommand {
    private VkRepository vkRepository;

    public CommunityCommand(DefaultAbsSender absSender, VkRepository vkRepository) {
        super(absSender);

        this.vkRepository = vkRepository;
    }

    @Override
    protected CompletableFuture<Map<String, String[]>> findAll(String id) {
        return vkRepository.findAllByCommunity(id);
    }

    @Override
    protected String getValidateMessage() {
        return "You must enter the community ID to grab music";
    }
}
