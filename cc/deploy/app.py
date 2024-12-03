import json
import tensorflow as tf
import numpy as np
from flask import Flask, request, jsonify
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

# Route for predicting with the complaint detector model
@app.route('/models/complaint', methods=['POST'])
def predict_complaint():
    try:
        data = request.get_json()
        input_complaint = data.get('input')
        if not input_complaint:
            logger.warning("No input data provided")
            return jsonify({'success': False, 'message': 'Input data is required'}), 400
        
        if not isinstance(input_complaint, str):
            logger.warning("Invalid input type: expected string")
            return jsonify({'success': False, 'message': 'Input must be a string'}), 400

        preprocessed_complaint = preprocess_complaint(input_complaint)

        if not preprocessed_complaint:
            logger.warning("Preprocessed complaint is empty")
            return jsonify({'success': False, 'message': 'Invalid input data'}), 400

        input_tensor = np.array([preprocessed_complaint])

        predictions = complaint_model.predict(input_tensor).tolist()

        # Convert predictions to True/False based on threshold
        thresholded_predictions = [pred >= 0.5 for pred in predictions[0]]

        logger.info("Complaint prediction successful")
        return jsonify({
            'success': True,
            'message': 'Prediction successful',
            'predictions': thresholded_predictions[0]
        })

    except Exception as e:
        logger.error(f"Error during complaint prediction: {e}")
        return jsonify({'success': False, 'message': 'Server error', 'error': str(e)}), 500

# Route for predicting with the category detector model
@app.route('/models/category', methods=['POST'])
def predict_category():
    try:
        data = request.get_json()
        input_complaint = data.get('input')
        if not input_complaint:
            logger.warning("No input data provided")
            return jsonify({'success': False, 'message': 'Input data is required'}), 400
        
        if not isinstance(input_complaint, str):
            logger.warning("Invalid input type: expected string")
            return jsonify({'success': False, 'message': 'Input must be a string'}), 400

        cleaned_complaint = clean_complaint(input_complaint)

        if not cleaned_complaint:
            logger.warning("Cleaned complaint is empty")
            return jsonify({'success': False, 'message': 'Invalid input data'}), 400

        # Transform the preprocessed input using the vectorizer
        input_vectorized = count_vectorizer.transform([cleaned_complaint])

        predictions = category_model.predict_proba(input_vectorized).tolist()

        # Get the index of the class with the highest probability
        max_prediction_index = int(np.argmax(predictions[0]))

        logger.info("Category prediction successful")
        return jsonify({
            'success': True,
            'message': 'Prediction successful',
            'predictions': category[max_prediction_index]
        })

    except Exception as e:
        logger.error(f"Error during category prediction: {e}")
        return jsonify({'success': False, 'message': 'Server error', 'error': str(e)}), 500