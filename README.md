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

1. `--tgb-username`=your_telegram_bot
2. `--tgb-token`=your:telegram_bot_token
3. `--vk-remixsid`=your_vk_sid - From *remixsid* VK cookie
4. `--vk-uid`=your_vk_id

Bot commands
------------

- `/profile <id>` - Grab music from VK profile by ID of user. ID must be digit.

Developing
----------

### Requirements

- Gradle >= 4.3

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
