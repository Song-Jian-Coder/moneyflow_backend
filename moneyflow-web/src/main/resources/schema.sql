CREATE TABLE IF NOT EXISTS todo_item (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200)  NOT NULL,
    description VARCHAR(2000) NULL,
    completed   BOOLEAN       NOT NULL DEFAULT FALSE,
    priority    TINYINT       NOT NULL DEFAULT 1,
    due_date    DATETIME      NULL,
    created_at  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_completed ON todo_item (completed);
CREATE INDEX IF NOT EXISTS idx_priority ON todo_item (priority);
CREATE INDEX IF NOT EXISTS idx_due_date ON todo_item (due_date);
