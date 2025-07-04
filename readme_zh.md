[English](./readme.md) | 中文

# GrassLogin
一个简单的 Bukkit 登录插件。

目前版本：1.0.0

## 支持
GrassLogin 插件理论上支持 MC 1.13+ 的服务器（因为开发环境是 MC 1.13）。

老版本兼容性请自行测试。

Folia 核心（及其下游和分支）现在用不了 GrassLogin（以后会的）。

## 功能
- 注册和登录账号
- 限制未登录玩家行为（如聊天，命令等）
- MySQL，SQLite 数据库支持
- 配置文件版本检查
- 多语言支持

## 计划
- 更改密码
- 忘记密码
- 邮箱验证
- BC 端，Velocity 端的跨服支持

## Download
[最新正式版](https://github.com/BlockNeko-11/GrassLogin/releases/latest)

[最新构建版](https://github.com/BlockNeko-11/GrassLogin/actions?query=event:push+branch:dev)

**⚠警告：最新构建版极其不稳定，使用之前记得先备份。**

## 安装
1. 下载插件。
2. 把插件 jar 文件丢入 `plugins` 文件夹中。
3. 启动一次服务器，然后关服。
4. 编辑配置文件（`plugins/GrassLogin/config.yml`）里的数据库配置。
5. 再次开服。
6. 完成！

## 构建
1. 克隆仓库。
2. 在仓库目录下运行 `gradlew build`。
