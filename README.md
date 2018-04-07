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
    3. `--vk-remixsid`=your_vk_sid - From *remixsid* VK cookie
    4. `--vk-uid`=your_vk_id
- Optional
    1. `--trusted-tgb-users`=list_telegram_usernames, your_user_name, or_username_your_channel

Bot commands
------------

- `/profile <id>` - Grab music from VK profile by ID of user. ID must be digit. Example: `/profile 123456789`
- `/com <id>` - Grab music from VK community by ID of its. ID must be string. Example: `/com crazy.music`

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
