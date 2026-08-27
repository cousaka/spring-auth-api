INSERT INTO users (
    email,
    password,
    name,
    role,
    created_at,
    updated_at
)
VALUES (
    'test@example.com',
    '{bcrypt}$2a$10$yMZ9WWsThEwU8Fb6fbsFeukiidhJtHoSoLEuuNnMvUBCu70SjIeqm',
    'テストユーザー',
    'USER',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (email) DO NOTHING;
