# Requirements and Design

## Use Cases

- user want to track income, expense daily, weekly, yearly
- user can input transaction
- each transaction has category, amount, date
- user want to view the reports

## Core Entities

1. User Account
   - the user using tracking system
2. Expense
   - A single transaction or purchase recorded (e.g., $25 on groceries)
3. Income
   - Any recorded earnings (salary, freelance, gifts)
4. Transaction
   - A debit or credit event tied to an account (could include both income and expense)
5. Category
   - The type of expense (e.g., Food, Rent, Entertainment)

## Data Flows

- user create User Account -> store in user_account
- user add category -> store in category
- user record transaction
  - expense
    - add expense record (amount, category, date) -> store in expense
    - copy record to transaction -> store in transaction
  - incore
    - add income record (amount, category, date) -> store in income
    - copy record to transaction -> store in transaction

![Data Flow](/design/data-flow.png "Data Flow")

## Entity Relation Diagram (ERD)

- one user can create multiple expense records
- one user can create multiple income records
- one transaction record link to one category
- one income record link to one transaction record
- one expense record link to one transaction record

### Entities Attributes

| Entity      | Attributes                                                                                                                                                                                              |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| User        | <ul><li>id</li><li>name</li><li>created_date</li></ul>                                                                                                                                                  |
| Category    | <ul><li>id</li><li>name</li><li>type</li><li>created_date</li></ul>                                                                                                                                     |
| Income      | <ul><li>id</li><li>description</li><li>user_id</li><li>amount</li><li>category_id</li><li>income_date</li><li>created_date</li></ul>                                                                    |
| Expense     | <ul><li>id</li><li>description</li><li>user_id</li><li>amount</li><li>category_id</li><li>expense_date</li><li>created_date</li></ul>                                                                   |
| Transaction | <ul><li>id</li><li>description</li><li>user_id</li><li>amount</li><li>category_id</li><li>income_id</li><li>expense_id</li><li>transaction_type</li><li>transaction_date</li><li>created_date</li></ul> |

![ERD](/design/entity-relation-diagram.png "Entity Relation Diagram")

## API Endpoints

- **setup new user** `POST : /v1/user/signup`
- **add new category** `POST : /v1/category`
- **get list of category** `GET : /v1/categories`
- **add new income record** `POST : /v1/income`
- **read list income record** `GET : /v1/incomes`
- **add new expense record** `POST : /v1/expense`
- **read list expense record** `GET : /v1/expenses`

# Tech Stacks

| Tech                 | Description                  |
| -------------------- | ---------------------------- |
| Programming Language | Java                         |
| Framework            | Spring Boot, Spring Data JPA |
| Database             | H2                           |
| API                  | Restful                      |
| Authentication       | Spring Security              |
| Container            | Docker                       |
| API Documentation    | OpenAPI                      |
| Unit Test            | Spring Test, Mockito         |
