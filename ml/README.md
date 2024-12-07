# Machine Learning

This is the machine learning part of our project.

We leverage machine learning to streamline the process of routing user complaints to the government, making their work more efficient.

User complaints are automatically screened by our complaint detection model to ensure that inappropriate or irrelevant submissions are filtered out before reaching the government.

Once filtered, the complaints are automatically categorized by our category detection model, ensuring they are accurately sorted based on their type.

This approach simplifies the process for users by allowing them to submit complaints without worrying about classification, while also ensuring that only valid and well-categorized complaints are sent to the government, making it easier for them to review and address issues efficiently.

## Workflow

This is our machine learning workflow:

![ML Workflow](https://drive.google.com/file/d/17fDyNQgYad05aVnwrxHrL5dK3KQCLU-Y/view?usp=sharing "ML Workflow")

There are 5 major parts in our machine learning workflow which are Data Gathering, Data Preprocessing, Model Training, Model Evaluation, Model Deployment.

### Data Gathering

This is the part where we gather the data needed to build our model from scratch.

Our data is gathered from 2 sources which are Twitter and Google Maps Reviews and automatically categorized them based on keywords.

We then combined them and save them in a csv format.

### Data Preprocessing

This is the part where we clean and preprocess the data into a more readable format.

There are two part of this which are the preprocessing for our Complaint Detection Model and Category Detection Model.

#### Complaint Detection Model Preprocessing

We first clean them from any typo and foreign languages.

We then take a sample of those data and manually labeled them whether that data is a complaint or not.

We then clean those samples from noises and stopwords before using those samples as a training data for our first model.

#### Category Detection Model Preprocessing

We first filter the remaining data with the Complaint Detection Model until only complaint remains.

We then fix the miscategorized from those data.

We then clean those data from noises and stopwords before using those data as a training data for our second model.

### Model Training

We train the training data with many algorithms such as Logistic Regression, SVM, Random Forest, Naive Bayes, XGBoost and Bidirectional LSTM.

We then picked the best result from those algorithms.

#### Complaint Detection Model Training

In this model, we pick the one that is trained with Bidirectional LSTM because it gives us the best training result.

#### Category Detection Model Training

In this model, we pick the one that is trained with XGBoost because it gives us the best training result.

### Model Evaluation

We continuously evaluate and fine tune both model until it reach the desired performances.

We use two metrics to evaluate our models which are accuracy and F1-Score.

### Model Deployment

After the model is good enough, we then deploy those models on cloud run.

## Models

We create 2 models for this project which are complaint detection model and category detection model. For more detail, the model part is gonna described inside the `model/` folder. 

## Future Improvements

We plan to develop an additional model to automate directing complaints directly to the appropriate government authority so that it's easier for the user to file a complaint.