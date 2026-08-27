# Spring Auth API – 起動手順

このプロジェクトは **Docker Compose + VSCode DevContainer + Spring Boot + PostgreSQL** を利用しています。
以下の手順に従えば、開発環境を再現できます。

## 0. 初回セットアップ（1以降に詳しい手順記載）
### 1. Clone
``` bash
git clone https://github.com/cousaka/spring-auth-api.git
cd spring-auth-api
```

### 2. .env を作成
``` bash
cp .env.example .env
```

### 3. JWTシークレットを生成
``` bash
openssl rand -base64 32
```
生成された値を .env の JWT_SECRET に設定します。

POSTGRES_DB=spring_auth <br />
POSTGRES_USER=spring_auth <br />
POSTGRES_PASSWORD=各自で設定 <br />
JWT_SECRET=生成したJWTシークレット <br />

### 4. VSCodeでDevContainerを起動
VSCodeでプロジェクトを開き、

Dev Containers: Reopen in Container

を実行します。

Java / Maven / REST Clientなどの開発環境は自動でセットアップされます。

### 5. Spring Bootを起動
DevContainer内のターミナルで、
``` bash
mvn spring-boot:run
```

### 6. APIにアクセス
```
http://localhost:8080/
```

.env は秘密情報を含むため、Gitにコミットしないでください。
他の開発者は .env.example から各自 .env を作成します。


---

## 1. リポジトリを Clone

```bash
git clone https://github.com/cousaka/spring-auth-api.git
cd spring-auth-api
```

## 2.env を作成
.env はGit管理せず、各開発者がローカルで作成します。
プロジェクトルートにある .env.example をコピーします。

```bash
cp .env.example .env
```

## 3. JWTシークレットを設定
JWTの署名に使用する秘密鍵を生成します。
Linux / WSL / DevContainerなどでは、以下で生成できます。

```bash
openssl rand -base64 32
```
生成した値を .env の JWT_SECRET に設定します。

※ `JWT_SECRET はGitへコミットしないでください。`


## 4. VSCode DevContainer を開く

VSCode でこのフォルダを開き、左下の 「Reopen in Container」 を選択。

DevContainer が起動すると、
Java / Maven / Git などが自動セットアップされます。


## 5. PostgreSQLが起動していることを確認
DevContainer内のターミナルで、以下を実行
``` bash
getent hosts db
```
db が解決できればOK


## 6. Spring Boot の起動（DevContainer 内）

DevContainer のターミナルで以下を実行：
```bash
mvn spring-boot:run
```

アプリが起動すると、以下でアクセスできます：
```
http://localhost:8080/
```
