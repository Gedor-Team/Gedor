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

![Cloud Infrastructure](https://i.ibb.co.com/xM0bFMM/gedor-infra-drawio.png "Cloud Infrastructure")

### Overview

Our project infrastructure is hosted on Google Cloud Platform (GCP) and consists of multiple components to handle application requests, data storage, and machine learning model predictions. Below is a breakdown of the architecture:

**1. Gedor Android App**
The Gedor Android App serves as the interface for users to interact with the system. Users can submit complaints, which are sent as HTTP requests to the backend services.
It communicates with:
- The API Endpoint to fetch and save complaint data.
- The Model Endpoint to get predictions for complaint validation and categorization.

**2. Gedor API Endpoint**
- Hosted on: Google Cloud Run.
- Purpose: Manages CRUD operations for the complaints.
- Containerization: The API is packaged into a Docker container for easy deployment and scaling.
- Integrated with our CloudSQL database

**3. Gedor Model Endpoint**
- Hosted on: Google Cloud Run.
- Purpose: Provides predictions for Complaint detection (valid or invalid complaints) and Category detection (assigning the appropriate category to complaints).
- Containerization: The model services are packaged into a Docker container for streamlined deployment.
- Receives complaint data as HTTP requests and returns predictions to the Android App.

**4. CloudSQL database**
- Hosted on: Google Cloud SQL.
- Purpose: Stores data.
- Acts as the central database for storing and retrieving information used by the API Endpoint.

## Future Improvements

- We are planning on Building CI/CD pipeline with GithubActions to automate our workflow
- We are planning on adding CloudCDN and Load Balancing to handle our traffic if our traffic is becoming heavy
- We are planning to use BigQuery and Looker to make our data analytics easier.