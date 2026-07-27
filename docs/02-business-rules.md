# Business Rules

## Customer

- Customer must register.
- Customer can browse without login.
- Customer must login before checkout.
- Customer can save multiple addresses.
- Customer can cancel order before shipping.

---

## Admin

- Admin manages products.
- Admin manages inventory.
- Admin manages orders.
- Admin manages banners.
- Admin manages coupons.

---

## Product

- Every product belongs to one category.
- Product may have multiple images.
- Product can have multiple variants.
- Product inventory must never become negative.

---

## Order

- Every order belongs to one customer.
- Every order contains one or more products.
- Order status changes sequentially.

Pending

↓

Confirmed

↓

Packed

↓

Shipped

↓

Delivered

Cancelled is possible before shipping.