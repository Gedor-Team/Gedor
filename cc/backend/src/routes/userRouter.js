const express = require('express');
const router = express.Router();
const bookController = require('../controllers/userController'); // Asumsikan path relatif ini sesuai dengan struktur direktori Anda

//menambahkan user
router.post('/', bookController.addUser);

// Mendapatkan semua user
router.get('/', bookController.getAllUser);

// Mendapatkan user berdasarkan user_id
router.get('/:user_id', bookController.getUserById);

// Menghapus user berdasarkan user_id
router.delete('/:user_id', bookController.deleteUser);

// Update user
router.patch('/:user_id', bookController.updateUser);

module.exports = router;