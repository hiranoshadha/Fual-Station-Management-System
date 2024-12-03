
# 🚀 Fuel Station Management System  

A comprehensive **Fuel Management System** designed to streamline operations, monitor resources, and enhance efficiency for fuel stations. Built with a modern tech stack:  
- **Frontend**: React with Tailwind CSS  
- **Backend**: Spring Boot  
- **Database**: MySQL (Workbench)  

---

## 🌟 Features  

### 🌐 User Roles  
1. **Customer**  
   - Home Page Access  
   - User Registration and Login  
2. **Admin Types**  
   - **Sales Manager**: Monitor and manage sales records.  
   - **Inventory Manager**: Oversee fuel stock levels and inventory.  
   - **Supplier Manager**: Manage supplier details and track orders.  

### ✨ Core Functionalities  
- **Authentication**: Secure login and registration.  
- **Role-Based Access**: Distinct dashboards and permissions for each user role.  
- **Fuel Inventory Tracking**: Real-time updates on fuel stocks.  
- **Sales Management**: Detailed sales analytics and reporting.  
- **Supplier Management**: Supplier database and order tracking.  

---

## 🛠️ Tech Stack  

**Frontend**  
- React.js  
- Tailwind CSS  

**Backend**  
- Spring Boot (Java)  

**Database**  
- MySQL (Workbench for schema design and data management)  

---

## 🚀 Getting Started  

### Prerequisites  
Ensure you have the following installed:  
- [Node.js](https://nodejs.org/)  
- [MySQL](https://dev.mysql.com/downloads/workbench/)  
- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)  
- [Spring Boot CLI](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html)  

### Installation  

#### 1. Clone the repository  
```bash  
git clone https://github.com/username/fuel-management-system.git  
cd fuel-management-system  
```  

#### 2. Frontend Setup  
```bash  
cd frontend  
npm install  
npm start  
```  

#### 3. Backend Setup  
- Open the backend project in your preferred IDE (e.g., IntelliJ IDEA or Eclipse).  
- Configure your database connection in the `application.properties` file:  
```properties  
spring.datasource.url=jdbc:mysql://localhost:3306/fuel_management  
spring.datasource.username=your_mysql_username  
spring.datasource.password=your_mysql_password  
spring.jpa.hibernate.ddl-auto=update  
```  
- Run the Spring Boot application.  

#### 4. Database Setup  
- Import the provided SQL schema file into **MySQL Workbench**.  

#### 5. Access the App  
- Frontend: `http://localhost:3000`  
- Backend API: `http://localhost:8080/api`  

---

## 📂 Project Structure  

### Frontend  
```
frontend/  
├── src/  
│   ├── components/  
│   ├── pages/  
│   ├── services/  
│   └── App.js  
├── tailwind.config.js  
└── package.json  
```  

### Backend  
```
backend/  
├── src/main/java/com/fuelmanagement/  
│   ├── controllers/  
│   ├── models/  
│   ├── repositories/  
│   └── services/  
├── src/main/resources/  
│   ├── application.properties  
└── pom.xml  
```  

---

## 🔐 Authentication and Authorization    
- **Role-Based Access Control (RBAC)** to define permissions for each user type.  

---

## 📊 ER Diagram  
Here’s the basic database structure for the system:  

```plaintext
Customer (id, name, email, password, etc.)  
Admin (id, name, role, etc.)  
FuelInventory (id, fuelType, stockLevel, etc.)  
Sales (id, customerId, fuelType, quantity, price, etc.)  
Supplier (id, name, contactInfo, etc.)  
```  

---

## 🤝 Contributing  

1. Fork the repository.  
2. Create a new branch (`feature/your-feature-name`).  
3. Commit your changes.  
4. Push to the branch.  
5. Open a pull request.  

---

## 📜 License  
This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for more details.  

---

## 👨‍💻 Authors  
- **Hiran Oshadha** - *Full-Stack Development*  
- Contributions are welcome!  
