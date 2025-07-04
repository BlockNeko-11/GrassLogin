English | [中文](./readme_zh.md)

# GrassLogin
A simple bukkit authentication plugin for Minecraft servers.

Current version: 1.0.0

## Support
GrassLogin plugin should support Minecraft 1.13+ in theory.

Folia (and its downstream and forks) is not supported now (will support in the future version).

## Features
- Register and login accounts
- Limit the unauthenticated players' behaviour (e.g. chat, commands)
- MySQL, SQLite database support
- Config file version verification
- Multi-language messages

## TODO
- Change password
- Forget password
- Mail verification
- BungeeCord, Velocity support

## Download
[Latest Release](https://github.com/BlockNeko-11/GrassLogin/releases/latest)

[Latest Build](https://github.com/BlockNeko-11/GrassLogin/actions?query=event:push+branch:dev)

**CAUTION: Latest build may include incompatible or breaking changes, so please make backup before using the latest build.**

## Installation
1. Download the plugin.
2. Drop the plugin jar into your `plugins` folder.
3. Run server and stop the server.
4. Edit the database config in `plugins/GrassLogin/config.yml`.
5. Start the server again.
6. Done!

## Build
1. Clone the repository.
2. Run `gradlew build` in the repository directory.
