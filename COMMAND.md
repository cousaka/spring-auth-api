# Spring Auth API – 開発用コマンドまとめ

Spring Boot（Java）と PostgreSQL を Docker Compose + VSCode DevContainer で動かすための操作まとめ。

---

## 🐳 Docker コンテナ操作

### コンテナ起動（通常）
``` bash
docker compose up -d
```

### コンテナ停止
``` bash
docker compose down
```

## 🐘 PostgreSQL（db）コンテナ操作

### DB コンテナに入る
``` bash
docker exec -it spring_auth_devcontainer-db-1 bash
```

### psql を起動
``` bash
psql -U spring_auth -d spring_auth
```

### ロール一覧
``` bash
\du
```

### テーブル一覧
``` bash
\dt
```

## 🚀 Spring Boot（Maven）操作

### アプリ起動
``` bash
mvn spring-boot:run
```

### クリーンビルド
``` bash
./mvnw clean package
```

### テスト実行
``` bash
./mvnw test
```

### テスト実行
``` bash
./mvnw dependency:resolve
```
