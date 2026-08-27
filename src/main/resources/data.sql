-- ========================================
-- 初期ユーザーデータ
-- ========================================

-- テストユーザー
-- ログイン情報:
--   メールアドレス: test@example.com
--   パスワード: password
INSERT INTO users (
    id,
    email,
    password,
    name,
    role,
    created_at,
    updated_at
)
VALUES (
    1,
    'test@example.com',
    '{bcrypt}$2a$10$yMZ9WWsThEwU8Fb6fbsFeukiidhJtHoSoLEuuNnMvUBCu70SjIeqm',
    'テストユーザー',
    'USER',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (email) DO NOTHING;


-- テスト管理者
-- ログイン情報:
--   メールアドレス: admin@example.com
--   パスワード: password
INSERT INTO users (
    id,
    email,
    password,
    name,
    role,
    created_at,
    updated_at
)
VALUES (
    0,
    'admin@example.com',
    '{bcrypt}$2a$10$yMZ9WWsThEwU8Fb6fbsFeukiidhJtHoSoLEuuNnMvUBCu70SjIeqm',
    '管理者',
    'ADMIN',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (email) DO NOTHING;
