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
CREATE INDEX IF NOT EXISTS idx_created_at ON todo_item (created_at);
CREATE INDEX IF NOT EXISTS idx_due_date ON todo_item (due_date);

-- 政策报告表
CREATE TABLE IF NOT EXISTS policy_report (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(200)  NOT NULL,
    summary      VARCHAR(2000) NULL,
    category     VARCHAR(50)   NOT NULL,
    source       VARCHAR(100)  NULL,
    publish_date DATE          NULL,
    source_url   VARCHAR(500)  NULL,
    document_url VARCHAR(500)  NULL,
    created_at   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_category ON policy_report (category);
CREATE INDEX IF NOT EXISTS idx_publish_date ON policy_report (publish_date);
CREATE INDEX IF NOT EXISTS idx_report_created_at ON policy_report (created_at);
