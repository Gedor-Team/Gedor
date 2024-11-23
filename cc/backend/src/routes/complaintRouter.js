const express = require('express');
const router = express.Router();
const complaintController = require('../controllers/complaintController'); // Asumsikan path relatif ini sesuai dengan struktur direktori Anda

//menambahkan komplain
router.post('/', complaintController.addComplaint);

// Mendapatkan semua komplain
router.get('/', complaintController.getAllComplaints);

// Mendapatkan komplaint berdasarkan complaint_id
router.get('/:complaint_id', complaintController.getComplaintById);

// Mendapatkan komplain berdasarkan user_id
router.get('/users/:user_id', complaintController.getComplaintsByUserId);

// Mendapatkan komplaint berdasarkan gov_id
router.get('/govs/:gov_id', complaintController.getComplaintsByGovId);

// Menghapus user berdasarkan user_id
router.delete('/:complaint_id', complaintController.deleteComplaint);

// Update user
router.patch('/:complaint_id', complaintController.updateComplaint);

module.exports = router;