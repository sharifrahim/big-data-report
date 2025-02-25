-- 1. Create main_tasks table
CREATE TABLE main_tasks (
    id              BIGSERIAL       PRIMARY KEY,
    type  VARCHAR(50)     NOT NULL,              -- Type of the main task
    description     TEXT            NULL,                  -- Optional description
    status          VARCHAR(50)     NOT NULL DEFAULT 'pending', -- Status of the main task
    scheduled_at    TIMESTAMP       NULL,                  -- Scheduled start time
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(), -- Creation timestamp
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW()  -- Last updated timestamp
);

-- 2. Create tasks table with subscribers_email column
CREATE TABLE tasks (
    id              BIGSERIAL       PRIMARY KEY,
    reference       VARCHAR(255)    NOT NULL,              -- Unique reference for queue/external
    main_task_id    BIGINT          NOT NULL,              -- Foreign key to main_tasks
    type       		VARCHAR(50)     NOT NULL,              -- Type of the sub-task
    status          VARCHAR(50)     NOT NULL DEFAULT 'pending', -- Status of the sub-task
    queued_at       TIMESTAMP       NULL,                  -- Time the sub-task was queued
    executed_at     TIMESTAMP       NULL,                  -- Execution start time
    completed_at    TIMESTAMP       NULL,                  -- Completion time
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(), -- Creation timestamp
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW(), -- Last updated timestamp
    subscribers_email VARCHAR(255)  NULL,                  -- Email of subscribers
    CONSTRAINT fk_main_task
        FOREIGN KEY (main_task_id)
        REFERENCES main_tasks (id)
        ON DELETE CASCADE
);

CREATE TABLE subscribers (
    id               BIGSERIAL       PRIMARY KEY,
    email            VARCHAR(255)    NOT NULL,
    report_type      VARCHAR(100)    NOT NULL,
    period_from      DATE            NOT NULL,
    period_to        DATE            NOT NULL,
    status           VARCHAR(50)     NOT NULL DEFAULT 'active',
    created_at       TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    payer_name VARCHAR(255) NOT NULL,
    payer_email VARCHAR(255) NOT NULL,
    merchant_email VARCHAR(255) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);





