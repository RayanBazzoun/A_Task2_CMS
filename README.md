# CMS API Documentation

## Overview

This document provides a detailed explanation of the database design, API endpoints, and validation rules for the CMS (Card Management System) project.

---

## Database Design

### 1. **Account**
- **Table**: `Account`
- **Fields**:
    - `id` (UUID, Primary Key)
    - `status` (String: ACTIVE/INACTIVE)
    - `balance` (Decimal)

### 2. **Card**
- **Table**: `card`
- **Fields**:
    - `id` (UUID, Primary Key)
    - `status` (String: ACTIVE/INACTIVE)
    - `expiry` (Date)
    - `cardnumber` (String, Encrypted)
    - `account_id` (Foreign Key → Account.id)
- **Relationships**:
    - Many Cards per Account (Many-to-One)

### 3. **Transaction**
- **Table**: `transaction_amount`
- **Fields**:
    - `id` (UUID, Primary Key)
    - `card_id` (Foreign Key → Card.id)
    - `transaction_type` (String: DEBIT/CREDIT)
    - `transaction_date` (DateTime)
    - `transaction_amount` (Decimal)
- **Relationships**:
    - Many Transactions per Card (Many-to-One)

---

## API Endpoints

### **Account APIs**
**Base Path:** `/accounts`

| Method | Endpoint                      | Description                                   | Request Body           |
|--------|-------------------------------|-----------------------------------------------|------------------------|
| POST   | `/accounts`                   | Create a new account                          | Decimal `balance`      |
| GET    | `/accounts`                   | Get all accounts                              | —                      |
| GET    | `/accounts/{accountId}`       | Get details of a specific account             | —                      |
| PUT    | `/accounts/{accountId}/status`| Update account status (ACTIVE/INACTIVE)       | String `status`        |
| DELETE | `/accounts/{accountId}`       | Delete an account                             | —                      |
| GET    | `/accounts/cards/{accountID}` | Get all cards for a specific account          | —                      |

### **Card APIs**
**Base Path:** `/cards`

| Method | Endpoint                      | Description                                   | Request Body           |
|--------|-------------------------------|-----------------------------------------------|------------------------|
| POST   | `/cards`                      | Create a new card for an account              | UUID `accountId`       |
| GET    | `/cards/{cardId}`             | Get card details (with decrypted cardnumber)  | —                      |
| PUT    | `/cards/{cardId}/status`      | Update card status (ACTIVE/INACTIVE)          | String `status`        |

### **Transaction APIs**

| Method | Endpoint        | Description                           | Request Body                                                                  |
|--------|-----------------|---------------------------------------|-------------------------------------------------------------------------------|
| POST   | `/transacitons` | Create a transaction through the Card | UUID `cardId`<br/>BigDecimal `TransactionAmount`<br/>String `TransactionType` |


## Validation Rules

### **Account**
- Status must be either `"ACTIVE"` or `"INACTIVE"`.
- Balance should be a valid non-negative decimal number.

### **Card**
- Can only be created for an existing account.
- Status must be either `"ACTIVE"` or `"INACTIVE"`.
- Expiry is set to 5 years from creation.
- Card number is generated randomly (16 digits) and stored encrypted.
- Card status changes validated to allowed values only.
- Card details retrieval decrypts card number before returning.

### **Transaction**
- Card must exist and be `"ACTIVE"`.
- Card must not be expired.
- Associated account must be `"ACTIVE"`.
- For `"DEBIT"`:
    - Account must have sufficient balance.
    - Balance is decremented by `transactionAmount`.
- For `"CREDIT"`:
    - Balance is incremented by `transactionAmount`.
- Only `"DEBIT"` or `"CREDIT"` are allowed as transaction types.
- Any invalid status or type throws an error.

---
