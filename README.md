# Bank Account Management System

A simple **Bank Account Management System** built with **Java Spring Boot** with a **web frontend** (HTML, CSS, JS) for managing bank accounts.  

---

## Features

- Create new bank accounts with auto-generated account numbers
- Deposit and withdraw money
- Check account balance
- View all accounts
- Apply interest to all accounts
- Data persistence using local file storage (`accounts.txt`)
- Functional web frontend

---

## Folder Structure

bankapp/
│
├─ src/
│ ├─ main/
│ │ ├─ java/com/bankapp/ # Backend Java code
│ │ └─ resources/
│ │ └─ static/ # Frontend files (HTML, CSS, JS)
│ │ ├─ index.html
│ │ ├─ style.css
│ │ └─ script.js
│ └─ test/ # Unit tests (optional)
├─ target/ # Compiled JAR
├─ pom.xml # Maven project file
└─ README.md



---

## Frontend

- HTML/CSS/JS files are in `src/main/resources/static/`
- Access in browser at:


---

## Setup & Run Locally

1. **Clone the repository**

```bash
git clone https://github.com/<username>/bankapp.git
cd bankapp

---

./mvnw clean package -DskipTests
./mvnw spring-boot:run
http://localhost:8080/

