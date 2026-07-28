CREATE TABLE payments (

    id BIGSERIAL PRIMARY KEY,

    order_id BIGINT NOT NULL,

    payment_method VARCHAR(50) NOT NULL,

    payment_status VARCHAR(50) NOT NULL,

    transaction_id VARCHAR(255),

    amount NUMERIC(10,2) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
);