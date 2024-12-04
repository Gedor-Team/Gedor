const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController'); // Asumsikan path relatif ini sesuai dengan struktur direktori Anda

//menambahkan user
router.post('/', userController.addUser);

// Mendapatkan semua user
router.get('/', userController.getAllUser);

// Mendapatkan user berdasarkan user_id
router.get('/:userID', userController.getUserById);

// Mendapatkan user berdasarkan user_id
router.get('/login/:username', userController.getUserByUsername);

// Menghapus user berdasarkan user_id
router.delete('/:userID', userController.deleteUser);

// Update user
router.patch('/:userID', userController.updateUser);

module.exports = router;