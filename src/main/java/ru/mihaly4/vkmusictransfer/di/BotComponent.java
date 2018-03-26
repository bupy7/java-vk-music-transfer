package ru.mihaly4.vkmusictransfer.di;

import dagger.Component;
import ru.mihaly4.vkmusictransfer.Bot;

@Component(modules = BotModule.class)
@SharedScope
public interface BotComponent {
    Bot makeBot();
}
