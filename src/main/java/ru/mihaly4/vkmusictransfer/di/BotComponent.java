package ru.mihaly4.vkmusictransfer.di;

import dagger.Component;
import ru.mihaly4.vkmusictransfer.Bot;

import javax.inject.Singleton;

@Component(modules = BotModule.class)
@Singleton
public interface BotComponent {
    Bot makeBot();
}
