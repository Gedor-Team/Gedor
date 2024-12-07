# Cloud Computing

This is the cloud computing part of our project.

## API

We create 2 apis for this project which are for our CRUD services and our models.

### CRUD API

This API is stored in the `backend/` folder and it's purpose is to perform CRUD services for our database.

**1. Key Features**

- Connects to a MySQL database for data management.
- Provides endpoints for handling user, complaint, and other related data.
- Implements data validation and secure handling of sensitive information.

**2. Tech Stack**
- Node.js  
- Express
- MySQL

### Models API

This API is stored in the `deploy/` folder and it's purpose is to perform inference for our complaint detection model and category detection model.

**1. Key Features**

- Handles inference request for our models.
- Integrates our machine learning models with our app.

**2. Tech Stack**
- Flask
- Gunicorn

## Deployment and Infrastructure

Our Cloud Infrastructure can be seen in this picture below: ![Cloud Infrastructure](https://i.ibb.co.com/xM0bFMM/gedor-infra-drawio.png "Cloud Infrastructure")

## Future Improvements

- We are planning on Building CI/CD pipeline with GithubActions to automate our workflow
- We are planning on adding CloudCDN and Load Balancing to handle our traffic if our traffic is becoming heavy
- We are planning to use BigQuery and Looker to make our data analytics easier.