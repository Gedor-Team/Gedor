import json
import tensorflow as tf
import numpy as np
from flask import Flask, request, jsonify
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
import re

app = Flask(__name__)

# Load stopwords and word_index.json
with open('utils/stop_words.json', 'r', encoding='utf-8') as f:
    stopwords = json.load(f)

with open('utils/bidirectional_lstm/word_index.json', 'r', encoding='utf-8') as f:
    word_index = json.load(f)

# Initialize stemmer
factory = StemmerFactory()
stemmer = factory.create_stemmer()

# Load the model (modify the path as needed)
complaint_model = tf.keras.models.load_model('utils/bidirectional_lstm/bidirectional_lstm_model.h5')

def clean_complaint(complaint):
    complaint = re.sub(r'@\w+', '', complaint)  # Remove usernames
    complaint = re.sub(r'http\S+', '', complaint)  # Remove URLs
    complaint = re.sub(r'[^\w\s]', '', complaint)  # Remove punctuation
    complaint = re.sub(r'\s+', ' ', complaint).strip()  # Remove extra spaces
    complaint = re.sub(r'[\u200B-\u200D\uFEFF\u3164]+', '', complaint)  # Remove zero-width characters
    return complaint

def remove_stopwords(complaint):
    complaint = complaint.lower()
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
            return jsonify({'success': False, 'message': 'Input data is required'}), 400

        preprocessed_complaint = preprocess_complaint(input_complaint)

        if not preprocessed_complaint:
            return jsonify({'success': False, 'message': 'Invalid input data'}), 400

        input_tensor = np.array([preprocessed_complaint])

        predictions = complaint_model.predict(input_tensor).tolist()

        return jsonify({
            'success': True,
            'message': 'Prediction successful',
            'predictions': predictions
        })

    except Exception as e:
        return jsonify({'success': False, 'message': 'Server error', 'error': str(e)}), 500

# # Route for predicting with the category detector model
# @app.route('/models/category', methods=['POST'])
# def predict_category():
#     try:
#         data = request.get_json()
#         input_complaint = data.get('input')
#         if not input_complaint:
#             return jsonify({'success': False, 'message': 'Input data is required'}), 400

#         preprocessed_complaint = preprocess_complaint(input_complaint)

#         input_tensor = np.array([preprocessed_complaint])

#         predictions = model.predict(input_tensor).tolist()

#         return jsonify({
#             'success': True,
#             'message': 'Prediction successful',
#             'predictions': predictions
#         })

#     except Exception as e:
#         return jsonify({'success': False, 'message': 'Server error', 'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)