# E-commerce
### Explanation of File Dependencies and User Flow

#### File Dependencies

1. **Model Classes**
    - **OurUser.java**: Represents the user entity in the database.
    - **Product.java**: Represents the product entity in the database.
    - **OurUserInfoDetails.java**: Implements `UserDetails` for Spring Security, mapping user details to Spring Security's user structure.

2. **Repository Interfaces**
    - **OurUserRepo.java**: Provides CRUD operations for `OurUser`.
    - **ProductRepo.java**: Provides CRUD operations and a custom search method for `Product`.

3. **Service Classes**
    - **OurUserInfoDetailsService.java**: Implements `UserDetailsService` to load user-specific data for Spring Security authentication.
    - **ProductService.java**: Provides methods to handle product-related operations such as fetching, searching, and disabling products.
    - **OrderService.java**: Handles order processing, including adding products to the cart and disabling them upon checkout.

4. **Configuration Class**
    - **SecurityConfig.java**: Configures Spring Security, specifying authentication, authorization, and password encoding.

5. **Exception Class**
    - **ProductNotFoundException.java**: Custom exception thrown when a product is not found.

#### Endpoints

1. **Authentication and Authorization**
    - `/login`: GET and POST requests for user login.
    - `/register`: GET and POST requests for user registration.

2. **Product Endpoints**
    - `/products`: GET request to list all products (paginated).
    - `/products/{id}`: GET request to fetch details of a specific product by ID.
    - `/products/search`: GET request to search for products by category (paginated).

3. **Cart and Order Endpoints**
    - `/cart/add`: POST request to add a product to the cart.
    - `/cart/checkout`: POST request to checkout the cart.
    - `/order/confirm`: POST request to confirm delivery and delete the product from the database.

#### User Flow and File Responsibilities

1. **User Registration and Login**
    - **Flow**: User navigates to `/register` to create an account. Once registered, they can log in via `/login`.
    - **Files Involved**:
        - **OurUser.java**: Stores user data.
        - **OurUserRepo.java**: Saves and retrieves user data.
        - **OurUserInfoDetailsService.java**: Loads user data for authentication.
        - **SecurityConfig.java**: Configures the security settings, including login and registration paths.

2. **Product Management**
    - **Flow**: User can view products on the `/products` page, search products via `/products/search`, and view specific product details at `/products/{id}`.
    - **Files Involved**:
        - **Product.java**: Represents the product entity.
        - **ProductRepo.java**: Fetches product data from the database.
        - **ProductService.java**: Handles business logic for fetching and searching products.
        - **SecurityConfig.java**: Ensures only authenticated users can access certain endpoints if required.

3. **Cart and Order Processing**
    - **Flow**: User adds products to the cart via `/cart/add`. They can then proceed to checkout at `/cart/checkout`, and confirm delivery through `/order/confirm`.
    - **Files Involved**:
        - **OrderService.java**: Handles adding products to the cart and processing orders.
        - **ProductService.java**: Updates product availability.
        - **ProductNotFoundException.java**: Handles errors when products are not found.

### Summary of Each File

1. **OurUser.java**
    - Represents a user in the database.
    - Contains fields for email, password, and roles.

2. **Product.java**
    - Represents a product in the database.
    - Contains fields for name, description, features, image, price, availability, and category.

3. **OurUserRepo.java**
    - Repository interface for `OurUser`.
    - Extends `JpaRepository` to provide CRUD operations.

4. **ProductRepo.java**
    - Repository interface for `Product`.
    - Extends `JpaRepository` to provide CRUD operations.
    - Contains a custom method for searching products by category.

5. **OurUserInfoDetailsService.java**
    - Implements `UserDetailsService` to load user data for authentication.
    - Uses `OurUserRepo` to fetch user details from the database.

6. **OurUserInfoDetails.java**
    - Implements `UserDetails` to map user data to Spring Security's user structure.
    - Contains user details such as email, password, and roles.

7. **ProductService.java**
    - Provides methods to handle product-related operations.
    - Uses `ProductRepo` to interact with the database.

8. **OrderService.java**
    - Handles order processing.
    - Uses `ProductRepo` to update product availability and process cart items.

9. **SecurityConfig.java**
    - Configures Spring Security.
    - Sets up authentication, authorization, and password encoding.

10. **ProductNotFoundException.java**
    - Custom exception for handling cases when a product is not found in the database.
