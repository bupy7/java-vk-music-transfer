package ru.mihaly4.vkmusictransfer.helper;

import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ArgsHelperTest {
    @Test
    public void getTrustTgbUid() {
        ArgsHelper helper = createHelper();

        List<String> users = helper.getTrustedTgbUsers();

        assertEquals(5, users.size());
        assertEquals("peter", users.get(0));
        assertEquals("Ivan-Mahno", users.get(1));
        assertEquals("sergey_stupa", users.get(2));
        assertEquals("ivan-mahno.sergey_stupa", users.get(3));
        assertEquals("ivan-mahno_4.sergey_stupa_5", users.get(4));
    }

    @Nullable
    private ArgsHelper createHelper() {
        try {
            return new ArgsHelper(new String[]{
                    "--tgb-token=1234:abcd",
                    "--tgb-username=telegram_bot",
                    "--trusted-tgb-users=peter,Ivan-Mahno, sergey_stupa , ivan-mahno.sergey_stupa ,ivan-mahno_4.sergey_stupa_5"
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
