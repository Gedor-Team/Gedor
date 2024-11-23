const express = require('express');
const router = express.Router();
const governmentController = require('../controllers/governmentController'); // Adjusted to governmentController

// Add a new government
router.post('/', governmentController.addGovernment);

// Get all governments
router.get('/', governmentController.getAllGovernment);

// Get government by govID
router.get('/:gov_id', governmentController.getGovernmentById);

// Delete government by govID
router.delete('/:gov_id', governmentController.deleteGovernment);

// Update government by govID
router.patch('/:gov_id', governmentController.updateGovernment);

module.exports = router;