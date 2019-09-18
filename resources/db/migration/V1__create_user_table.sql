create TABLE user (
    id VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    tax_identifier VARCHAR(100) NOT NULL,
    crated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON update CURRENT_TIMESTAMP,
    CONSTRAINT PK_id PRIMARY KEY(id),
    CONSTRAINT UQ_email UNIQUE(email),
    CONSTRAINT UQ_tax_identifier UNIQUE(tax_identifier)
)