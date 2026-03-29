CREATE TABLE tb_order (
    order_id    VARCHAR(50) PRIMARY KEY,
    user_id     VARCHAR(50) NOT NULL,
    item_name   VARCHAR(100) NOT NULL,
    amount      NUMERIC(12, 2) NOT NULL,
    order_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);