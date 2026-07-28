ALTER TABLE orders
ADD COLUMN address_id BIGINT;

ALTER TABLE orders
ADD CONSTRAINT fk_order_address
FOREIGN KEY (address_id)
REFERENCES addresses(id);