package ru.mihaly4.vkmusictransfer.log;

import ru.mihaly4.vkmusictransfer.ui.Messager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLog implements ILog {
    private Messager messager = new Messager();

    @Override
    public void error(String message) {
        messager.println("[ERR@" + getTimestamp() +"] " + message);
    }

    @Override
    public void info(String message) {
        messager.println("[INFO@" + getTimestamp() +  "] " + message);
    }

    private String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
