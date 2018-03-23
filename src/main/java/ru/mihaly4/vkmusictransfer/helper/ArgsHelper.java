package ru.mihaly4.vkmusictransfer.helper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;

public class ArgsHelper {
    private CommandLine cli;

    public ArgsHelper(String args[]) throws ParseException {
        Options options = createOptions();

        CommandLineParser parser = new DefaultParser();
        cli = parser.parse(options, args);
    }

    public String getTgbToken() {
        return cli.getOptionValue("tgb-token") != null ? cli.getOptionValue("tgb-token") : "";
    }

    public String getTgbUsername() {
        return cli.getOptionValue("tgb-username") != null ? cli.getOptionValue("tgb-username") : "";
    }

    public String getVkRemixSid() {
        return cli.getOptionValue("vk-remixsid") != null ? cli.getOptionValue("vk-remixsid") : "";
    }

    public String getVkUid() {
        return cli.getOptionValue("vk-uid") != null ? cli.getOptionValue("vk-uid") : "";
    }

    private Options createOptions() {
        Options options = new Options();

        options.addRequiredOption(null, "tgb-username", true, "Username of your bot");
        options.addRequiredOption(null, "tgb-token", true, "Access token of your bot");
        options.addRequiredOption(null, "vk-remixsid", true, "Security identifier of your VK profile");
        options.addRequiredOption(null, "vk-uid", true, "User identifier of your VK profile");

        return options;
    }
}
