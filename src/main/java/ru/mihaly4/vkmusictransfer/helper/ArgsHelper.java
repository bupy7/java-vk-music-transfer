package ru.mihaly4.vkmusictransfer.helper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public int getVkUid() {
        return cli.getOptionValue("vk-uid") != null ? Integer.valueOf(cli.getOptionValue("vk-uid")) : 0;
    }

    public List<String> getTrustedTgbUsers() {
        if (cli.getOptionValue("trusted-tgb-users") == null) {
            return new ArrayList<>();
        }

        Pattern regex = Pattern.compile("[\\w-.]+");
        Matcher matcher = regex.matcher(cli.getOptionValue("trusted-tgb-users"));
        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }

    private Options createOptions() {
        Options options = new Options();

        options.addRequiredOption(null, "tgb-username", true, "Username of your bot");
        options.addRequiredOption(null, "tgb-token", true, "Access token of your bot");
        options.addRequiredOption(null, "vk-remixsid", true, "Security identifier of your VK profile");
        options.addRequiredOption(null, "vk-uid", true, "User identifier of your VK profile");

        options.addOption(null, "trusted-tgb-users", true, "Trusted usernames who can use functional");

        return options;
    }
}
