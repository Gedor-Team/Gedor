const express = require('express');
const router = express.Router();
const governmentController = require('../controllers/governmentController'); // Adjusted to governmentController

// Add a new government
router.post('/', governmentController.addGovernment);

// Get all governments
router.get('/', governmentController.getAllGovernment);

// Get government by govID
router.get('/:govID', governmentController.getGovernmentById);

// Delete government by govID
router.delete('/:govID', governmentController.deleteGovernment);

// Update government by govID
router.patch('/:govID', governmentController.updateGovernment);

module.exports = router;