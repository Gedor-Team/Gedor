# Cloud Computing

This is the cloud computing part of our project.

## API

We create 2 apis for this project which are for our CRUD services and our models.

**1. CRUD API**

This API is stored in the `backend/` folder and it's purpose is to perform CRUD services for our database.

### Key Features

- Connects to a MySQL database for data management.
- Provides endpoints for handling user, complaint, and other related data.
- Implements data validation and secure handling of sensitive information.

### Tech Stack
- Node.js  
- Express
- MySQL

**2. Models API**

This API is stored in the `deploy/` folder and it's purpose is to perform inference for our complaint detection model and category detection model.

### Key Features

- Handles inference request for our models.
- Integrates our machine learning models with our app.

### Tech Stack
- Flask
- Gunicorn

## Deployment and Infrastructure

## Future Improvements