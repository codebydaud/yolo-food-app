# YOLO: On-demand Food Ordering Platform
## Overview
YOLO is a food ordering platform designed to help customers create custom ideas and order custom recipes. With the help of an AI assistant, customers can submit ideas for custom recipes based on their preferences, and vendors (chefs) can generate personalized recipes. The platform supports two apps: Customer App and Vendor (Chef) App.

## Video Demo

https://github.com/user-attachments/assets/f1a45aa2-52bf-42d5-a7cf-e4886ce52dbf


## 1. Customer App
### Purpose
The YOLO Customer App allows users to design custom ideas with the help of an AI assistant and order them. Customers can request custom recipes based on their ideas or dietary preferences.

### Features
* User Registration: Allows new users to sign up for the platform. </br>
* User Authentication: Enables login via an IAM solution (e.g., Keycloak) with OAuth 2.0 security. </br>
* Create Recipe Ideas via AI Assistant: Users can input their own ideas or get idea suggestions from an AI assistant by providing prompts. They can also include dietary restrictions and preferences. </br>
* Submit Ideas: Customers can submit recipe ideas to request personalized suggestions from chefs. </br>
* View Recipes: Users can view custom recipes submitted by chefs against their ideas. </br>
* Order Recipe: Customers can order one or more selected recipes in their required quantities. </br>

## 2. Vendor (Chef) App
### Purpose
The YOLO Vendor (Chef) App allows chefs to create custom recipes based on customer ideas. Chefs can submit recipes with relevant details like serving size and price, and receive and manage orders.

### Features
* User Registration: Allows chefs to sign up for the platform. </br>
* User Authentication: Enables login via an IAM solution (Keycloak). </br>
* Display User Interests: Vendors can see a list of customer-submitted ideas, with the latest ideas shown first. </br>
* Create Recipes via AI Assistant: Chefs can input recipes for a selected idea and optionally use AI assistance to generate recipe suggestions. </br>
* Submit Recipes to Customers: Chefs can submit up to three recipes for each idea, which are sent to the requesting customer. </br>
* Receive Orders: Vendors can receive and manage orders, including details like quantity and customer address. </br>
* Update Order Status: Chefs can update the order status (e.g., Processing, Dispatched). </br>
## Technology Stack
### Frontend: 
* Vue.js with Tailwind CSS for responsive and modern design. </br>
* Axios for API calls. </br>
* Vue Router for routing.
  
### Backend:
* Java Spring Boot (REST API).
* Spring Security for JWT-based authentication and authorization.

### Database: 
* MySQL.

### Security: 
* OAuth 2.0 (Keycloak) for secure API access and user authentication.

## Screenshots (Customer App)

|  Home Screen                              |  Generate Idea Screen                              |
|-----------------------------------------|-----------------------------------------|
| ![Home](https://github.com/user-attachments/assets/6087b42e-ade6-41cb-9132-7e251bf0a5b2) | ![Generate IDea with Data](https://github.com/user-attachments/assets/88abdcf7-da99-4ec0-9184-bc818e7977ce) |

|  My Ideas Screen                              |  Idea Detail Popup                               |
|-----------------------------------------|-----------------------------------------|
| ![My ideas](https://github.com/user-attachments/assets/6818d4df-4b6e-4750-b1ed-527b09ad53ce) | ![Idea Popup](https://github.com/user-attachments/assets/b3b82159-bed3-4157-89e5-41d10237d602) |

|  Recipe List Screen                              |  Recipe Detail Screen                               |
|-----------------------------------------|-----------------------------------------|
| ![Recipe page for customer](https://github.com/user-attachments/assets/d81bfc86-1c82-424e-a99f-b66053ddc464) | ![recipe detail for customer](https://github.com/user-attachments/assets/8d8ac04c-d1b2-4fb1-8891-82989de243f2) |

|  My Orders Screen                              |  Profile Screen                               |
|-----------------------------------------|-----------------------------------------|
| ![my orders](https://github.com/user-attachments/assets/031a261c-e08c-4541-90d8-f2e9d2038786) | ![profile](https://github.com/user-attachments/assets/bd73cbba-67e0-4a39-87c1-ceed8b035e52) |

## Screenshots (Chef App)

|  Home Screen                              |  Customer's Idea List for Chef Screen                              |
|-----------------------------------------|-----------------------------------------|
| ![chef home](https://github.com/user-attachments/assets/27f71f8e-d7a0-4a40-b86a-124d0dabc519) | ![idea list for chef](https://github.com/user-attachments/assets/45d9cfd6-7320-4bca-9d11-7b66c4981620) |

|  Generate Recipe Screen                              |  My Recipes List Screen                               |
|-----------------------------------------|-----------------------------------------|
| ![generate recipe](https://github.com/user-attachments/assets/053f0058-d25f-4237-b5e3-32b964c98153) | ![all recipes](https://github.com/user-attachments/assets/2450dad6-8baa-4309-bd3d-414c9aaca358) |

|  Recipe Detail Screen                              |  Orders Screen                               |
|-----------------------------------------|-----------------------------------------|
| ![recipe detail chef](https://github.com/user-attachments/assets/4f438bcc-73fb-471f-a859-6e58da50c162) | ![order page chef](https://github.com/user-attachments/assets/09c5b81e-1f6e-4391-9a32-158f58317d2d) |
