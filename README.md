java-vk-music-transfer
=================

[![Build Status](https://travis-ci.org/bupy7/java-vk-music-transfer.svg?branch=master)](https://travis-ci.org/bupy7/java-vk-music-transfer)

Transfer music from VK to Telegram.

TODO
----

- DiC

Requirements
------------

- Java >= 8.0

Run
---

**For using:**

```bash
$ java -jar vk-music-transfer-1.0.0.jar <ARGUMENTS>
```

**Arguments:**

1. `--tgb-username`=your_telegram_bot
2. `--tgb-token`=your:telegram_bot_token
3. `--vk-remixsid`=your_vk_sid
4. `--vk-uid`=your_vk_id


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
