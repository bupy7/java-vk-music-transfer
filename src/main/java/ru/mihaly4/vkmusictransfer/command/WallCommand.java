package ru.mihaly4.vkmusictransfer.command;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import ru.mihaly4.vkmusictransfer.repository.VkRepository;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @since 1.1.0
 */
public class WallCommand extends AbstractGrabCommand {
    private VkRepository vkRepository;

    public WallCommand(DefaultAbsSender absSender, VkRepository vkRepository) {
        super(absSender);

        this.vkRepository = vkRepository;
    }

    @Override
    protected CompletableFuture<Map<String, String[]>> findAll(String id) {
        return vkRepository.findAllByWall(id);
    }

    @Override
    protected String getValidateMessage() {
        return "You must enter the community ID to grab music";
    }
}
