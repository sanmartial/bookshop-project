At the end of this HW you should have a ready to use Online Book App API, so you need to 
implement the missing functionality. Below you will see the detailed requirements what should be in your project.

User Use Cases (means these operations are allowed to users with role USER)
Use cases are the set of actions that could be performed by some actor (in this case user). 
Here are the list of use cases that we will cover in this part of HW:

Order Processing:

As a User, I want to place an order, so I can purchase the books in my shopping cart. I will:
Send a POST request to /api/orders to place an order.
As a User, I want to view my order history, so I can track my past purchases. I will:
Send a GET request to /api/orders to retrieve my order history.
Order Item Retrieval:

As a User, I want to view the items in my order, so I can check the details of my purchase. I will:
Send a GET request to /api/orders/{orderId}/items to retrieve all OrderItems for a specific order.
As a User, I want to view a specific item in my order, so I can check its details. I will:
Send a GET request to /api/orders/{orderId}/items/{id} to retrieve a specific OrderItem within an order.
Admin Use Cases (means these operations are allowed to users with role ADMIN)
Use cases are the set of actions that could be performed by some actor (in this case admin). 
Here are the list of use cases that we will cover in this part of HW:

Order Management

As an Admin, I want to update order status, so I can manage the order processing workflow. I will:
Send a PUT request to /api/orders/{id} to update the status of an order.
Domain models (entities)
There is a list of all entities that should be present in the project:

User: Contains information about the registered user including their authentication details and personal information.
Role: Represents the role of a user in the system, for example, admin or user.
Book: Represents a book available in the store.
Category: Represents a category that a book can belong to.
ShoppingCart: Represents a user's shopping cart.
CartItem: Represents an item in a user's shopping cart.
Order: Represents an order placed by a user.
OrderItem: Represents an item in a user's order.
Order entity
Order: Represents an order placed by a user.

Add a new entity Order with the next fields:
id (Long, PK)
user (User, not null)
status (Status (enum), not null)
total (BigDecimal, not null)
orderDate (LocalDateTime, not null)
shippingAddress (String, not null)
orderItems (Set<OrderItem>)
OrderItem entity
OrderItem: Represents an item in a user's order.

Add a new entity OrderItem with the next fields:
id (Long, PK)
order (Order, not null)
book (Book, not null)
quantity (int, not null)
price (BigDecimal, not null)
General requirements
Don't forget to use Liquibase
Don't forget to implement soft delete approach
Add Pagination, Sorting, and Swagger to all controllers you have
Endpoints
User Endpoints: These endpoints should be done in the previous PRs

Book Endpoints: These endpoints should be done in the previous PRs

Category Endpoints: These endpoints should be done in the previous PRs

Shopping Cart Endpoints: These endpoints should be done in the previous PRs

Order Endpoints:

POST: /api/orders (Place an order) Example of request body:
{
"shippingAddress": "Kyiv, Shevchenko ave, 1"
}

GET: /api/orders (Retrieve user's order history)
Example of response body:

[
{
"id": 101,
"userId": 456,
"orderItems": [
{
"id": 1,
"bookId": 789,
"quantity": 2
},
{
"id": 2,
"bookId": 790,
"quantity": 1
}
],
"orderDate": "2023-07-25T10:15:30",
"total": 29.98,
"status": "COMPLETED"
},
{
"id": 102,
"userId": 456,
"orderItems": [
{
"id": 3,
"bookId": 791,
"quantity": 1
}
],
"orderDate": "2023-07-23T15:20:45",
"total": 14.99,
"status": "PENDING"
}
]

PATCH: /api/orders/{id} (Update order status) Example of request body:
{
"status": "DELIVERED"
}

OrderItem Endpoints: Since OrderItem entities are related to a specific order, you can create nested 
endpoints under an Order. This way, you can manage OrderItems within the context of their associated order.

GET: /api/orders/{orderId}/items (Retrieve all OrderItems for a specific order)
Example of response body:

[
{
"id": 1,
"bookId": 789,
"quantity": 2
},
{
"id": 2,
"bookId": 790,
"quantity": 1
}
]

GET: /api/orders/{orderId}/items/{itemId} (Retrieve a specific OrderItem within an order)
Example of response body:

{
"id": 2,
"bookId": 790,
"quantity": 1
}

POST, PUT, and DELETE endpoints for OrderItem may not be necessary, as OrderItems are typically created, 
updated, or removed when a user interacts with their shopping cart or places an order.

SECURITY REQUIREMENTS
Available for non authenticated users:
POST: /api/auth/register
POST: /api/auth/login
Available for users with role USER
GET: /api/books
GET: /api/books/{id}
GET: /api/categories
GET: /api/categories/{id}
GET: /api/categories/{id}/books
GET: /api/cart
POST: /api/cart
PUT: /api/cart/cart-items/{cartItemId}
DELETE: /api/cart/cart-items/{cartItemId}
GET: /api/orders
POST: /api/orders
GET: /api/orders/{orderId}/items
GET: /api/orders/{orderId}/items/{itemId}
Available for users with role ADMIN
POST: /api/books/
PUT: /api/books/{id}
DELETE: /api/books/{id}
POST: /api/categories
PUT: /api/categories/{id}
DELETE: /api/categories/{id}
PATCH: /api/orders/{id}
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
Project description
We are going to implement an app for Online Book store. We will implement it step by step. 
In this app we will have the following domain models (entities):

User: Contains information about the registered user including their authentication details and personal information.
Role: Represents the role of a user in the system, for example, admin or user.
Book: Represents a book available in the store.
Category: Represents a category that a book can belong to.
ShoppingCart: Represents a user's shopping cart.
CartItem: Represents an item in a user's shopping cart.
Order: Represents an order placed by a user.
OrderItem: Represents an item in a user's order.
NOTE: Keep in mind, this is a general description of the project, that we will implement during this module. 
We will implement it part by part. For this purpose you needs to create a new GitHub repo where your project will be. 
Then you can share this repo link in your CV, and it will be a proof of work for you potential interviewers

People involved:
Shopper (User): Someone who looks at books, puts them in a basket (shopping cart), and buys them.
Manager (Admin): Someone who arranges the books on the shelf and watches what gets bought.
Things Shoppers Can Do:
Join and sign in:
Join the store.
Sign in to look at books and buy them.
Look at and search for books:
Look at all the books.
Look closely at one book.
Find a book by typing its name.
Look at bookshelf sections:
See all bookshelf sections.
See all books in one section.
Use the basket:
Put a book in the basket.
Look inside the basket.
Take a book out of the basket.
Buying books:
Buy all the books in the basket.
Look at past receipts.
Look at receipts:
See all books on one receipt.
Look closely at one book on a receipt.
Things Managers Can Do:
Arrange books:
Add a new book to the store.
Change details of a book.
Remove a book from the store.
Organize bookshelf sections:
Make a new bookshelf section.
Change details of a section.
Remove a section.
Look at and change receipts:
Change the status of a receipt, like "Shipped" or "Delivered".