

Add a new entity ShoppingCart with the next fields:
id (Long, PK)
user (User, not null)
cartItems (Set<CartItem>)

CartItem entity
CartItem: Represents an item in a user's shopping cart.

Add a new entity CartItem with the next fields:
id (Long, PK)
shoppingCart (ShoppingCart, not null)
book (Book, not null)
quantity (int, not null)
HINT: It may be helpful to add @Named("bookFromId") default Book bookFromId(Long id) { // your implementation here} to the BookMapper interface

General requirements
Don't forget to use Liquibase
Don't forget to implement soft delete approach
Add Pagination, Sorting, and Swagger to all controllers you have
Endpoints
User Endpoints: These endpoints should be done in the previous PRs

Book Endpoints: These endpoints should be done in the previous PRs

Category Endpoints: These endpoints should be done in the previous PRs

Shopping Cart Endpoints:

GET: /api/cart (Retrieve user's shopping cart)
Example of response body:

{
"id": 123,
"userId": 456,
"cartItems": [
{
"id": 1,
"bookId": 789,
"bookTitle": "Sample Book 1",
"quantity": 2
},
{
"id": 2,
"bookId": 790,
"bookTitle": "Sample Book 2",
"quantity": 1
}
]
}

POST: /api/cart (Add book to the shopping cart)
Example of request body:

{
"bookId": 2,
"quantity": 5
}

PUT: /api/cart/cart-items/{cartItemId} (Update quantity of a book in the shopping cart)
Example of request body:

{
"quantity": 10
}

DELETE: /api/cart/cart-items/{cartItemId} (Remove a book from the shopping cart)
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
Available for users with role ADMIN
POST: /api/books/
PUT: /api/books/{id}
DELETE: /api/books/{id}
POST: /api/categories
PUT: /api/categories/{id}
DELETE: /api/categories/{id}
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