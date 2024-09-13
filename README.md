# YOLO: On-demand Food Ordering Platform
## Overview
YOLO is a food ordering platform designed to help customers create custom ideas and order custom recipes. With the help of an AI assistant, customers can submit ideas for custom recipes based on their preferences, and vendors (chefs) can generate personalized recipes. The platform supports two apps: Customer App and Vendor (Chef) App.

## 1. Customer App
### Purpose
The YOLO Customer App allows users to design custom ideas with the help of an AI assistant and order them. Customers can request custom recipes based on their ideas or dietary preferences.

### Features
User Registration: Allows new users to sign up for the platform. </br>
User Authentication: Enables login via an IAM solution (e.g., Keycloak) with OAuth 2.0 security. </br>
Create Recipe Ideas via AI Assistant: Users can input their own ideas or get idea suggestions from an AI assistant by providing prompts. They can also include dietary restrictions and preferences. </br>
Submit Ideas: Customers can submit recipe ideas to request personalized suggestions from chefs. </br>
View Recipes: Users can view custom recipes submitted by chefs against their ideas. </br>
Order Recipe: Customers can order one or more selected recipes in their required quantities. </br>
### Technology Stack
#### Backend:
Java Spring Boot (REST API).

#### Frontend: 
Vue.js

#### Database: 
MySQL.

#### Security:
OAuth 2.0 for secure API access and user authentication.

## 2. Vendor (Chef) App
### Purpose
The YOLO Vendor (Chef) App allows chefs to create custom recipes based on customer ideas. Chefs can submit recipes with relevant details like serving size and price, and receive and manage orders.

### Features
User Registration: Allows chefs to sign up for the platform. </br>
User Authentication: Enables login via an IAM solution (e.g., Keycloak). </br>
Display User Interests: Vendors can see a list of customer-submitted ideas, with the latest ideas shown first. </br>
Create Recipes via AI Assistant: Chefs can input recipes for a selected idea and optionally use AI assistance to generate recipe suggestions. </br>
Submit Recipes to Customers: Chefs can submit up to three recipes for each idea, which are sent to the requesting customer. </br>
Receive Orders: Vendors can receive and manage orders, including details like quantity and customer address. </br>
Update Order Status: Chefs can update the order status (e.g., Processing, Dispatched). </br>
### Technology Stack
#### Backend:
Java Spring Boot (REST API).

#### Frontend: 
Vue.js

#### Database: 
MySQL.

#### Security: 
OAuth 2.0 for secure API access and user authentication.
