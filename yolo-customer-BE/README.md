# YOLO - Custom meal idea generating and Recipe Ordering Platform

Welcome to the YOLO project! This platform allows users to order custom recipes generated using manually entered or AI generated ideas. Our goal is to make meal planning easier and more personalized. This README will guide you through setting up the project on your local machine and explain how to contribute to it.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

YOLO Customer BE is an on-demand food ordering platform that empowers users to create personalized ideas and order personalized recipes directly through the platform. With an intuitive interface and AI-driven idea generation, YOLO makes it simple for anyone to craft the perfect meal.

## Features

- **Custom Idea Creation:** Users can create and submit their own meal ideas.
- **AI Assistant:** Get AI-generated ideas based on your preferences and dietry restrictions.
- **Order Management:** Easily manage orders through the platform.
- **Secure Login:** OAuth 2.0 for secure authentication, signup/signin with google using IAM solution KEYCLOAK

## Tech Stack

- **Frontend:** Vue.js 3
- **Backend:** Java Spring Boot 3.3.2
- **Database:** MySQL
- **Security:** OAuth 2.0

## Getting Started
### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21**
- **Node.js and npm**
- **MySQL**
- **KEYCLOAK**

### Installation (For Windows)

1. **Clone the Repository:**

    - Navigate to the desired directory where you wish to create project
    - Open windows powershell/cmd in that directory or use any code editor with embeded terminal
    - Run the following commands
        - For YOLO Customer APP
            git clone https://github.com/sharjeelnisar2/yolo-custoemr-BE.git

2.    **Create MYSQL Database:**

        - Create a database for customer app and one for vendor app
          
3.    **In root directory, create a file named: "**
        - env.properties
          
3.    **Set Environment Variables in the above file:**
        #### DB Config Variables
        - DB_URL
        - DB_USERNAME
        - DB_PASSWORD
        #### AI Key
        - AI_KEY

5.    **Run Project:**

        You can run project using any Java code editor i.e. IntelliJ
        To run project using IntelliJ 
        - Add a new app configuration JDK 21, Main Class com.yolo.customer.CustomerApplication
        - Run file
        
        OR 

        You can run project using maven 
        In order to run project using maven
        - mvn clean install
        - mvn spring-boot:run 
      
## Usage

- In Order to use all the features of application following projects should also be running:
        For installation instructions on YOLO Customer Front end Please refer to the README.md file in the following repository:
        - https://github.com/sharjeelnisar2/yolo-customer-FE
        For installation instructions on YOLO Chef Back end Please refer to the README.md file in the following repository:
        - https://github.com/sharjeelnisar2/yolo-chef-BE
        For installation instructions on YOLO Chef Front end Please refer to the README.md file in the following repository:
        - https://github.com/sharjeelnisar2/yolo-chef-FE

- Once the applications are running, you can:
    - Register/Login to the platform
    - Create Custom Ideas manually or using the AI assistant
    - Submit Ideas and View Recipes submitted by YOLO CHEF application againt your ideas
    - Order Recipes directly from the platform

## Contributing

- In Order to contribute to the Project:
    - Fork the repository
    - Create a new branch for your feature or bugfix 
        - git checkout -b feature/your-feature-name
    - Make your changes and commit them
        - git commit -m "Add some feature"
    - Push to your branch
        - git push origin feature/your-feature-name

## License

    This project is licensed under the MIT License - see the LICENSE file for details

## Contact

    For any questions or feedback, feel free to reach out to the project maintainers:
        Name - email@example.com
        GitHub Issues - https://github.com/sharjeelnisar2/yolo-custoemr-BE/issues

