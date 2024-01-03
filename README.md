At the end of this HW you should add a support for a new entity: Category, so you need to implement the missing functionality. Below you will see the detailed requirements what should be in your project.

User Use Cases (means these operations are allowed to users with role USER)
Use cases are the set of actions that could be performed by some actor (in this case user). Here are the list of use cases that we will cover in this part of HW:

Category Browsing:

As a User, I want to browse categories, so I can find books by category. I will:
Send a GET request to /api/categories to retrieve all categories.
Send a GET request to /api/categories/{id}/books to retrieve books by a specific category.
Admin Use Cases (means these operations are allowed to users with role ADMIN)
Category Management

As an Admin, I want to create a new category so books can be categorized. I will:
Send a POST request to /api/categories with the details of the new category.
As an Admin, I want to update the details of a category so the categories are up-to-date. I will:
Send a PUT request to /api/categories/{id} with the updated details of the category.
As an Admin, I want to remove a category, so it is no longer available. I will:
Send a DELETE request to /api/categories/{id} to remove the category.
Domain models (entities)
There is a list of all entities that should be present in the project after this HW:

Book: Represents a book available in the store.
User: Contains information about the registered user including their authentication details and personal information.
Role: Represents the role of a user in the system, for example, admin or user.
Category: Represents a category that a book can belong to.
Category entity
Category: Represents a category that a book can belong to.

#Add a new entity Category with the next fields:
#id (Long, PK)
#name (String, not null)
#description (String)

#Book class now should have the following field: 
#private Set<Category> categories = new HashSet<>();

#HINT: you can use the next method in the BookRepository.class:
#List<Book> findAllByCategoryId(Long categoryId);

#Implement the CategoryRepository interface that will use JpaRepository interface
#Implement DTO classes for Category entity

#Modify the BookMapper class to have such methods:
#BookDto toDto(Book book);
#Book toEntity(CreateBookRequestDto bookDto);
#BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book); 
(HINT: BookDtoWithoutCategoryIds class could be used as a response class for @GetMapping("/{id}/books") endpoint)
@AfterMapping default void setCategoryIds(@MappingTarget BookDto bookDto, Book book)

#Add CategoryMapper class with such methods:
#CategoryDto toDto(Category category);
#Category toEntity(CategoryDto categoryDTO);

#Implement CategoryService interface with methods and CategoryServiceImpl class:
#List findAll();
#CategoryDto getById(Long id);
#CategoryDto save(CategoryDto categoryDto);
#CategoryDto update(Long id, CategoryDto categoryDto);
#void deleteById(Long id);

#Add CategoryController class with such methods:
#public CategoryDto createCategory(CategoryDto categoryDto)
#public List getAll()
#public CategoryDto getCategoryById(Long id)
#public CategoryDto updateCategory(Long id, CategoryDto categoryDto)
#public void deleteCategory(Long id)
#public List getBooksByCategoryId(Long id) (endpoint: "/{id}/books")

General requirements
Don't forget to use Liquibase
Don't forget to implement soft delete approach
Add Pagination, Sorting, and Swagger to all controllers you have
Endpoints
Below you will find the list of endpoints that should be in your project at the end of this HW.

User Endpoints: These endpoints should be done in the previous PRs

Book Endpoints: These endpoints should be done in the previous PRs, but pay attention you have added the categoryIds field to the DTO classes

Category Endpoints:

POST: /api/categories (Create a new category)
Example of request body:

{
"name": "Fiction",
"description": "Fiction books"
}

GET: /api/categories (Retrieve all categories)
Example of response body:

{
"id": 1,
"name": "Fiction",
"description": "Fiction books"
}

GET: /api/categories/{id} (Retrieve a specific category by its ID)
PUT: /api/categories/{id} (Update a specific category)
Example of request body:

{
"name": "Fiction",
"description": "Fiction books"
}

DELETE: /api/categories/{id} (Delete a specific category)
GET: /api/categories/{id}/books (Retrieve books by a specific category)
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
Available for users with role ADMIN
POST: /api/books/
PUT: /api/books/{id}
DELETE: /api/books/{id}
POST: /api/categories
PUT: /api/categories/{id}
DELETE: /api/categories/{id}
Create a PR to your existing repository with your course project and share the link to the PR as a HW solution.
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