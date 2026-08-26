# Spring Auth API – 起動手順

このプロジェクトは **Docker Compose + VSCode DevContainer + Spring Boot + PostgreSQL** を利用しています。
以下の手順に従えば、開発環境を再現できます。

---

## 1. リポジトリを Clone

```bash
git clone https://github.com/cousaka/spring-auth-api.git
cd spring-auth-api
```

## 2. VSCode DevContainer を開く

VSCode でこのフォルダを開き、左下の 「Reopen in Container」 を選択。

DevContainer が起動すると、
Java / Maven / Git などが自動セットアップされます。

## 3. Spring Boot の起動（DevContainer 内）

DevContainer のターミナルで以下を実行：
```bash
mvn spring-boot:run
```

アプリが起動すると、以下でアクセスできます：
```
http://localhost:8080/
```
