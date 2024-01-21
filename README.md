# Online Book Store

## Project Description

Welcome to the Book Store Web Application! This project was inspired by the need for a robust and secure platform to manage various aspects of a book store, including user authentication, book and category management, order processing, and shopping cart functionality.

## People Involved

- **Shopper (User):** Someone who browses books, adds them to a basket, and makes purchases.
- **Manager (Admin):** Someone who manages the book inventory and monitors sales.

## Functionalities

### For Shoppers

#### Join and Sign In
- Join the store.
- Sign in to explore and buy books.

#### Browse and Search for Books
- View all books.
- Detailed view of a single book.
- Find a book by typing its name.

#### Explore Bookshelf Sections
- See all bookshelf sections.
- View all books in one section.

#### Use the Basket
- Add a book to the basket.
- View and manage the basket.

#### Buying Books
- Purchase all the books in the basket.
- View past receipts.

#### View Receipts
- See all books on one receipt.
- Detailed view of one book on a receipt.

### For Managers

#### Manage Books
- Add a new book to the store.
- Modify book details.
- Remove a book from the store.

#### Organize Bookshelf Sections
- Create a new bookshelf section.
- Modify section details.
- Remove a section.

#### Manage Receipts
- Change the status of a receipt, like "Shipped" or "Delivered."

## Technologies Used

- Spring Boot: Backend application development.
- Spring Security: Secure authentication and authorization.
- Spring Data JPA: Simplifies database interactions.
- Swagger: Provides interactive API documentation.
- MySQL Database: Used for quick development and testing.
- Lombok: Reduces boilerplate code.
- Docker: Containerization.

## Controllers and Functionalities

### AuthenticationController
- `POST /api/auth/registration`: Create a new user.
- `POST /api/auth/login`: Authenticate and retrieve a JWT token.

### BookController
- `GET /api/books`: Retrieve all books with pagination and sorting.
- `GET /api/books/{id}`: Retrieve a book by ID.
- `POST /api/books`: Create a new book (admin role required).
- `PUT /api/books/{id}`: Update a book by ID (admin role required).
- `DELETE /api/books/{id}`: Delete a book by ID (admin role required).
- `GET /api/books/search`: Search books based on criteria with pagination and sorting.

### CategoryController
- `POST /api/categories`: Create a new category (admin role required).
- `GET /api/categories`: Retrieve all categories with pagination and sorting.
- `GET /api/categories/{id}`: Retrieve a category by ID.
- `PUT /api/categories/{id}`: Update a category (admin role required).
- `DELETE /api/categories/{id}`: Delete a category (admin role required).
- `GET /api/categories/{id}/books`: Retrieve books by category ID with pagination and sorting.

### OrderController
- `POST /api/orders`: Place a new order.
- `GET /api/orders`: Retrieve user's order history.
- `PATCH /api/orders/{id}`: Update order status.

### OrderItemController
- `GET /api/orders/{orderId}/items`: Retrieve all order items for a specific order.
- `GET /api/orders/{orderId}/items/{itemId}`: Retrieve a specific order item within an order.

### ShoppingCartController
- `GET /api/cart`: Retrieve user's shopping cart by ID.
- `POST /api/cart`: Add a book to the shopping cart.
- `PUT /api/cart/cart-items/{cartItemId}`: Update quantity of a book in the shopping cart.
- `DELETE /api/cart/cart-items/{cartItemId}`: Remove a book from the shopping cart.

## Running with Docker Compose

To set up the Online Book Store, follow these steps:

1. Clone the repository: `git clone https://github.com/sanmartial/bookshop-project.git`.
2. Ensure you have Java and Maven installed.
3. Customize the `docker-compose.yml` file for your environment.
4. Execute the following commands in the project root directory:
   ```bash
   mvn package
   docker-compose build
   docker-compose up
   
5. Access the Swagger documentation at `http://localhost:8088/swagger-ui/index.html`.  
   To log in as administrator use the following login and password   
    login: admin@gmail.com;  
    password: 12345678.


Feel free to explore the API, and for a more detailed walkthrough, check out our [demo video](https://www.loom.com/share/8c2d1e785f254d4fb25da225f669af09?sid=57788d74-77c7-469c-95e4-966f2fd055c5). 🚀

Thank you for watching, and happy exploring!
