package ru.mihaly4.vkmusictransfer.ui;

public class Messager implements IMessage {
    @Override
    public void println(String message) {
        System.out.println(message);
    }
}
