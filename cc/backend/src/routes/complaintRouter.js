const express = require('express');
const router = express.Router();
const complaintController = require('../controllers/complaintController'); // Asumsikan path relatif ini sesuai dengan struktur direktori Anda

//menambahkan komplain
router.post('/', complaintController.addComplaint);

// Mendapatkan semua komplain
router.get('/', complaintController.getAllComplaints);

// Mendapatkan komplaint berdasarkan complaint_id
router.get('/:complaintID', complaintController.getComplaintById);

// Mendapatkan komplain berdasarkan user_id
router.get('/users/:userID', complaintController.getComplaintsByUserId);

// Mendapatkan komplain berdasarkan gov_id
router.get('/govs/:govID', complaintController.getComplaintsByGovId);

// Menghapus komplain berdasarkan complaint_id
router.delete('/:complaintID', complaintController.deleteComplaint);

// Update komplain
router.patch('/:complaintID', complaintController.updateComplaint);

module.exports = router;