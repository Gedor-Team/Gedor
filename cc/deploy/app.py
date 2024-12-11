import json
import tensorflow as tf
import numpy as np
from flask import Flask, request, jsonify
from flask_restx import Api, Resource, fields
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
import re
import pickle
import logging

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

app = Flask(__name__)
api = Api(app, version="1.0.0", title="Gedor Models API", description="API for Gedor Classification Models")

# Define namespace
ns = api.namespace('models', description='Gedor Classification Model')

# Define request and response models
complaint_input_model = api.model('ComplaintInput', {
    'input': fields.String(required=True, description='Input text to predict', example="Pabrik di daerah situ menghasilkan asap yg bikin polusi tambah parah"),
})

complaint_response_model = api.model('ComplaintResponse', {
    'message': fields.String(description='Result message', example='Prediction successful'),
    'predictions': fields.Boolean(description='Whether the input is a complaint', example=True),
    'success': fields.Boolean(description='Success status', example=True),
})

# Define request and response models
category_input_model = api.model('CategoryInput', {
    'input': fields.String(required=True, description='Input text to predict category', example="Polusi udara di sekitar kantor"),
})

category_response_model = api.model('CategoryResponse', {
    'message': fields.String(description='Result message', example='Prediction successful'),
    'predictions': fields.String(description='Predicted category', example='polusi'),
    'success': fields.Boolean(description='Success status', example=True),
})

error_response_model = api.model('ErrorResponse', {
    'message': fields.String(description='Error message', example='Server error'),
    'success': fields.Boolean(description='Success status', example=False),
    'error': fields.String(description='Error details', example='Error message goes here'),
})

# Load stopwords and word_index.json
with open('utils/stop_words.json', 'r', encoding='utf-8') as f:
    stopwords = json.load(f)

with open('utils/bidirectional_lstm/word_index.json', 'r', encoding='utf-8') as f:
    word_index = json.load(f)

# Load the XGBoost model and vectorizer
with open('utils/xgboost/xgboost_cnt_model.pkl', 'rb') as f:
    category_model = pickle.load(f)

with open('utils/xgboost/xgboost_cnt_vectorizer.pkl', 'rb') as f:
    count_vectorizer = pickle.load(f)

# Initialize stemmer
factory = StemmerFactory()
stemmer = factory.create_stemmer()

category = [
    'lingkungan',
    'fasilitas umum',
    'kekeringan',
    'polusi',
    'lainnya',
    'layanan',
    'limbah',
    'sampah',
    'hutan'
]


# test
# Load the model (modify the path as needed)
complaint_model = tf.keras.models.load_model('utils/bidirectional_lstm/bidirectional_lstm_model.h5')

def clean_complaint(complaint):
    complaint = complaint.lower()
    complaint = re.sub(r'@\w+', '', complaint)  # Remove usernames
    complaint = re.sub(r'http\S+', '', complaint)  # Remove URLs
    complaint = re.sub(r'[^\w\s]', '', complaint)  # Remove punctuation
    complaint = re.sub(r'\s+', ' ', complaint).strip()  # Remove extra spaces
    complaint = re.sub(r'[\u200B-\u200D\uFEFF\u3164]+', '', complaint)  # Remove zero-width characters
    return complaint

def remove_stopwords(complaint):
    words = complaint.split(' ')
    words = [word for word in words if word not in stopwords]
    words = [stemmer.stem(word) for word in words]
    return ' '.join(words)

def tokenize_complaint(complaint):
    words = complaint.split(' ')
    tokenized_complaint = [word_index.get(word, 0) for word in words]
    return tokenized_complaint

def preprocess_complaint(complaint):
    cleaned_complaint = clean_complaint(complaint)
    cleaned_complaint = remove_stopwords(cleaned_complaint)
    tokenized_complaint = tokenize_complaint(cleaned_complaint)
    return tokenized_complaint

# Define resource
@ns.route('/complaint')
class ComplaintPrediction(Resource):
    @ns.expect(complaint_input_model)
    @ns.response(201, 'Complaint processed successfully', complaint_response_model)
    @ns.response(400, 'Invalid input', error_response_model)
    @ns.response(500, 'Server error', error_response_model)
    def post(self):
        """Predict whether the input is a complaint or not"""
        try:
            data = request.get_json()
            input_complaint = data.get('input')

            if not input_complaint:
                return {'success': False, 'message': 'Input data is required'}, 400

            if not isinstance(input_complaint, str):
                return {'success': False, 'message': 'Input must be a string'}, 400

            # Preprocess the complaint text (replace with actual logic)
            preprocessed_complaint = preprocess_complaint(input_complaint)

            if not preprocessed_complaint:
                return {'success': False, 'message': 'Invalid input data'}, 400

            input_tensor = np.array([preprocessed_complaint])

            # Mock prediction for example
            probability = complaint_model.predict(input_tensor).tolist()
            predictions = [pred >= 0.4 for pred in probability[0]]

            return {
                'message': 'Prediction successful',
                'predictions': predictions[0],
                'success': True,
            }, 201

        except Exception as e:
            return {'success': False, 'message': 'Server error', 'error': str(e)}, 500

# Complaint Category Route
@ns.route('/category')
class CategoryPrediction(Resource):
    @ns.expect(category_input_model)
    @ns.response(201, 'Prediction successful', category_response_model)
    @ns.response(400, 'Invalid input', error_response_model)
    @ns.response(500, 'Server error', error_response_model)
    def post(self):
        """Predict the category of a given complaint"""
        try:
            data = request.get_json()
            input_complaint = data.get('input')

            if not input_complaint:
                return {'success': False, 'message': 'Input data is required'}, 400

            if not isinstance(input_complaint, str):
                return {'success': False, 'message': 'Input must be a string'}, 400

            cleaned_complaint = clean_complaint(input_complaint)

            if not cleaned_complaint:
                return {'success': False, 'message': 'Invalid input data'}, 400

            # Mock vectorizer and prediction logic
            input_vectorized = count_vectorizer.transform([cleaned_complaint])  # Replace with real logic
            predictions = category_model.predict_proba(input_vectorized).tolist()
            max_prediction_index = int(np.argmax(predictions[0]))

            logger.info("Category prediction successful")
            return {
                'success': True,
                'message': 'Prediction successful',
                'predictions': category[max_prediction_index],
            }, 201

        except Exception as e:
            logger.error(f"Error during category prediction: {e}")
            return {'success': False, 'message': 'Server error', 'error': str(e)}, 500

# Add namespace to the API
api.add_namespace(ns, path='/models')