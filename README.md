java-vk-music-transfer
======================

[![Build Status](https://travis-ci.org/bupy7/java-vk-music-transfer.svg?branch=master)](https://travis-ci.org/bupy7/java-vk-music-transfer)

Transfer music from VK to Telegram.

Requirements
------------

- Java >= 8.0

Run
---

1. **Download** the latest release by [link](https://github.com/bupy7/java-vk-music-transfer/releases).

2. **Unarchive** the package.

3. **Execute:**

```bash
$ ./vk-music-transfer <ARGUMENTS>
```

or (for Windows)

```bash
$ ./vk-music-transfer.bat <ARGUMENTS>
```

**Arguments:**

- Required
    1. `--tgb-username`=your_telegram_bot
    2. `--tgb-token`=your:telegram_bot_token
- Optional
    1. `--trusted-tgb-users`=list_telegram_usernames, your_user_name, or_username_your_channel

**In chat with bot:**

```
Me:     /login +79998887766 my_pass
Bot:    You have logged successfully
Me:     /wall some_group
Bot:    Progress: 1/18

etc...
```

Bot commands
------------

- `/login <phone> <password>` - Login in VK. Example: `/login +79998887766 my_fucking_password`;
- `/audio <id>` - Grab music from audio page by ID of an user or a community. ID must be only digit. Example: `/audio 123456789` for user profiles or `/audio -123456789` for communities;
- `/wall <id>` - Grab music from wall page by ID of an user of a community. Example: `/wall crazy.music` or `/wall id123456789`.

Developing
----------

### Build

```bash
$ ./gradlew build
```

### Run

```bash
$ ./gradlew run -Prunargs="--ARGUMENTS=SEPARATE,--BY=COMMA"
```

### Run tests

```bash
$ ./gradlew test
```

License
-------

java-vk-music-transfer is released under the BSD-3-Clause License. See the bundled LICENSE.md for details.
