# Project Setup Guide

This guide will help you set up the necessary environment and dependencies to run the project.

## Prerequisites

Before you start, ensure that you have the following installed on your system:

- **Node.js** (https://nodejs.org/)
- **npm**
- **mysql database** 

## Installation Steps

1. **Install Node.js**  
Download and install Node.js from the [official website](https://nodejs.org/). This will also install npm (Node Package Manager).

2. **Install Dependencies**

```bash
npm install
```

## Build Steps

Add your mysql database credential in your .env file before building the app.

### Node.js without docker

Use this command if you want to run it without docker.
```bash
npm run dev
```

### Docker

Just build the Dockerfile with this command

```bash
docker build -t gedor-api .
```

And then run it with this command

```bash
docker run -d -p 8080:8080 --env-file .env --name gedor-api gedor-api
```

This will create and docker image and container with name gedor-api 

## API Documentation

   You can read the API documentation after you run the server from /api/docs page [API Docs](http://localhost:8080/api/docs).
