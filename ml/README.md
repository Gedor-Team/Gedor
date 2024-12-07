# Machine Learning

This is the machine learning part of our project.

We leverage machine learning to streamline the process of routing user complaints to the government, making their work more efficient.

User complaints are automatically screened by our complaint detection model to ensure that inappropriate or irrelevant submissions are filtered out before reaching the government.

Once filtered, the complaints are automatically categorized by our category detection model, ensuring they are accurately sorted based on their type.

This approach simplifies the process for users by allowing them to submit complaints without worrying about classification, while also ensuring that only valid and well-categorized complaints are sent to the government, making it easier for them to review and address issues efficiently.

## Workflow

This is our machine learning workflow:

![ML Workflow](https://i.ibb.co.com/kDsRZZG/ml-workflow-drawio.png "ML Workflow")

There are 5 major parts in our machine learning workflow which are Data Gathering, Data Preprocessing, Model Training, Model Evaluation, Model Deployment.

1.  **Data Gathering**

This is the part where we gather the data needed to build our model from scratch.

Our data is gathered from 2 sources which are Twitter and Google Maps Reviews and automatically categorized them based on keywords.

We then combined them and save them in a csv format.

2. **Data Preprocessing**

This is the part where we clean and preprocess the data into a more readable format.

There are two part of this which are the preprocessing for our Complaint Detection Model and Category Detection Model.

- **Complaint Detection Model Preprocessing**

We first clean them from any typo and foreign languages.

We then take a sample of those data and manually labeled them whether that data is a complaint or not.

We then clean those samples from noises and stopwords before using those samples as a training data for our first model.

- **Category Detection Model Preprocessing**

We first filter the remaining data with the Complaint Detection Model until only complaint remains.

We then fix the miscategorized from those data.

We then clean those data from noises and stopwords before using those data as a training data for our second model.

3. **Model Training**

We train the training data with many algorithms such as Logistic Regression, SVM, Random Forest, Naive Bayes, XGBoost and Bidirectional LSTM.

We then picked the best result from those algorithms.

- **Complaint Detection Model Training**

In this model, we pick the one that is trained with Bidirectional LSTM because it gives us the best training result.

- **Category Detection Model Training**

In this model, we pick the one that is trained with XGBoost because it gives us the best training result.

4. **Model Evaluation**

We continuously evaluate and fine tune both model until it reach the desired performances.

We use two metrics to evaluate our models which are accuracy and F1-Score.

5. **Model Deployment**

After the model is good enough, we then deploy those models on cloud run.

## Models

We create 2 models for this project which are complaint detection model and category detection model.

1. **Complaint Detection Model**

This model analyzes user input to determine whether it qualifies as a valid complaint.

Its purpose is to filter out spam inputs and ensure only genuine complaints are processed.

2. **Category Detection Model**

This model categorizes user complaints and assigns them to the appropriate category.

Its purpose is to organize complaints efficiently for better handling and faster resolution.

## How to use 

### Project Setup Guide

This guide will help you set up the necessary environment and dependencies to run the project.

### Prerequisites

Before you start, ensure that you have the following installed on your system:

- **Python** (https://www.python.org/)
- **pip** 

### Installation Steps

1. **Install Python**  
Download and install Python from the [official website](https://www.python.org/).

2. **Install Pip**
Install pip using the installation guide from the [pip documentation website](https://pip.pypa.io/en/stable/installation/).

3. **Install Virtual Environtmet**
We need to install this to avoid conflicts between libraries for this project.
This is how you set up your own virtual environtment

- **Install venv library**
Use this command to install the venv library
```bash
pip install venv
```
- **Setup venv**
You can follow this guide to setup your own virtual environtment with venv [guide](https://www.freecodecamp.org/news/how-to-setup-virtual-environments-in-python/).

4. **Install Dependencies**
Install all the required python library by typing this command in your terminal.
```bash
pip install -r requirements.txt
```

## Future Improvements

We plan to develop an additional model to automate directing complaints directly to the appropriate government authority so that it's easier for the user to file a complaint.