# Supplier and Inventory Management System

This project provides a comprehensive system for managing suppliers, contracts, orders, and inventory within a business. It is designed to handle supplier information, product details, contract agreements, and both regular and periodic orders. Additionally, it includes a separate module for managing product stock, categories, discounts, and generating reports.

## Tech Stack

- Java
- SQL (for database interactions, implied by `SQLException` and data persistence operations)

## Features

### Supplier and Contract Management Module
- **Supplier Management**: Add, delete, and view supplier details, including contact information, bank accounts, payment types, and associated companies.
- **Product Management**: Add new products with price and ID.
- **Contact Management**: Add contacts for suppliers.
- **Contract Management**: Create and manage contracts with suppliers, including product-specific quantity discounts.
- **Order Management**: Handle both one-time and periodic orders, allowing for product quantity specification and order updates.
- **Data Initialization and Clearing**: Functionality to initialize the system with sample data and clear all existing data.
- **Comprehensive Data Display**: View detailed information for suppliers, contracts, orders, and associated entities.

### Inventory Management Module
- **Product Management**: Add products to the store with details like barcode, name, stock amount, selling/manufacturing price, shelf number, manufacturer, and category.
- **Product Information**: View detailed information for all products or a single product.
- **Product Updates**: Change product price, name, minimum stock amount, and move products between categories.
- **Category Management**: Add, delete, and modify product categories and subcategories.
- **Discount Management**: Apply product-specific and category-wide discounts with defined start and end dates.
- **Item Management**: Add and remove items from storage and store, tracking expiration dates and quantities.
- **Reporting**: Generate various reports, including minimum stock reports and order reports.
- **Order Receiving**: Process received orders and update stock.
- **Alerts**: Generate alerts for low stock or other critical inventory conditions.
- **Data Loading/Removal**: Load initial data or remove all data from the inventory system.

## How to Run

1.  **Prerequisites**:
    - Java Development Environment (JDK) installed.
    - A compatible SQL database (details not specified in the provided code, but implied).

2.  **Compilation**:
    - Compile all Java files in the project. Ensure all necessary packages (`Backend`, `BusinessLayer`, `controllers`, `objects`, `com.company`) are correctly structured.
    - Example (adjust paths as necessary):
    ```bash
    javac com/company/*.java Backend/**/*.java BusinessLayer/*.java controllers/*.java objects/*.java
    ```

3.  **Execution**:
    - The project appears to have two main entry points: `MainController.java` for supplier/contract management and `Stockmain.java` for inventory management. The `Main.java` file (not provided, but referenced) likely serves as the overarching entry point to choose between these modules.
    - To run the supplier/contract management module (assuming `MainController` is the entry point):
    ```bash
    java com.company.MainController
    ```
    - To run the inventory management module (assuming `Stockmain` is the entry point):
    ```bash
    java com.company.Stockmain
    ```
    - Follow the on-screen menu prompts to interact with the system.


  ## Contact
 -feel free for any question

