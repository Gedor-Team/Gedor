# Project Setup Guide

This guide will help you set up the necessary environment and dependencies to run the project.

## Prerequisites

Before you start, ensure that you have the following installed on your system:

- **Python** (https://www.python.org/)
- **pip** 

## Installation Steps

1. **Install Python**  
Download and install Python from the [official website](https://www.python.org/).

2. **Install Pip**
Install pip using the installation guide from the [pip documentation website](https://pip.pypa.io/en/stable/installation/).

3. **Install Dependencies**
Install all the required python library by typing this command in your terminal.
```bash
pip install -r requirements.txt
```

## Build Steps

### built-in flask web server

Use this command if you want to run it without guvicorn (Because you can't use guvicorn on windows!) 

Note:

If you want to run it in built-in flask web server, you need to add this code below in the bottom of the app.py file.

```python
if __name__ == '__main__':
    app.run(debug=True)
```

Don't forget to delete it later if you want to run it with guvicorn.

```bash
python app.py
```

### Docker + guvicorn

Just build the Dockerfile with this command

```bash
docker build -t gedor-model-api .
```

And then run it with this command

```bash
docker run -d -p 5000:5000 --name gedor-models-api gedor-models-api
```

This will create and docker image and container with name gedor-models-api

## API Documentation

   You can read the API documentation after you run the server from the [base url](http://localhost:5000/).