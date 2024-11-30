# ReadVibes

Readvibes is a book review platform where users can explore a collection of books, leave reviews, provide ratings, and browse others’ feedback
Built using **Spring MVC** and **Spring Data JPA**, the application leverages **SQL Server** for data storage and ensures a smooth and efficient user experience.

## Features

- **Browse Books**:
  - View a collection of books with pagination.
  - Filter, search, and sort books for easy navigation.
- **Reviews and Ratings**:
  - Submit new reviews and ratings for books.
  - Edit existing reviews.
  - Average rating calculation for each book based on user reviews.
  - List and paginate reviews for a book.
- **User Authentication & Authorization**:
  - Users can register, log in, and access the platform base on their account.
  - Password is hashed and verified by **Bcrypt**
- **Responsive UI**:
  - A modern, user-friendly interface optimized for all devices.
- **Backend Integration**:
  - Built with Spring MVC and Spring Data JPA.
  - SQL Server as the database backend.

## Technologies Used

- **Backend**: Spring MVC, Spring Data JPA
- **Database**: SQL Server
- **Frontend**: JSP, HTML, CSS, JavaScript
- **Server**: Tomcat 11 (External)
- **Tools**: Maven, Eclipse

## Installation and Setup

Follow these steps to set up the project on your local machine:

### 1. Clone the Repository

Clone the repository from GitHub to your local machine and navigate into the project directory:

```bash
git clone https://github.com/chloenth/readvibes.git
cd readvibes
```

### 2. Database Setup

1. Ensure **SQL Server** is installed and running.
2. Create a database named `readvibes_db`.
3. Configure the database connection in the `application.properties` file. Open the `application.properties` file in the `src/main/resources` directory and update it with the following settings:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=readvibes_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
```

- Replace your-username and your-password with your actual SQL Server credentials.

### 3. Build and Run the Project

The project uses the embedded **Tomcat 11** server by default. To run the application:

```bash
mvn clean install
```

### 4. Run the Application

Start your server and access the application at `http://localhost:8080/ReadVibes/`.

## API Endpoints

| **Endpoint**                | **Method** | **Description**                                    |
| --------------------------- | ---------- | -------------------------------------------------- |
| `/books?sort?page`          | GET        | Fetch all books (and sort - optional) (paginated). |
| `/books?search?sort?page`   | GET        | Search books (and sort - optional) (paginated).    |
| `/books?filter?sort?page`   | GET        | Filter books (and sort - optional) (paginated).    |
| `/books/{id}`               | GET        | Fetch book details and reviews.                    |
| `/books/{id}/add-review`    | POST       | Add a new review.                                  |
| `/books/{id}/update-review` | PUT        | Update an existing review.                         |
| `/login`                    | POST       | Log in a user                                      |
| `/register`                 | POST       | Register a new user                                |

## How bcrypt is implemented for password hashing:

Hash passwords securely by using **bcrypt** algorithm. The hashing is done in the service layer, and the password is never stored in plaintext. Here's how bcrypt is implemented:

1. **Password Hashing**: The password is hashed using `BCryptPasswordEncoder` before storing it in the database.
2. **Password Verification**: During login, the hashed password is verified by comparing the entered password with the stored hash using `BCryptPasswordEncoder`.
