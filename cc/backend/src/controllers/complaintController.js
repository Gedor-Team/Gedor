const Complaint = require("../models/complaintModel"); // Import Complaint model from Sequelize models

const complaintController = {
  // Add a new complaint
  addComplaint: async (req, res) => {
    try {
      const {
        userID,
        govID,
        complaint,
        category,
        status,
        lokasi,
        kecamatan,
        kabupaten,
        provinsi,
      } = req.body;

      // Make sure userID and govID are passed in the request
      if (!userID || !govID) {
        return res.status(400).json({
          success: false,
          message: "userID and govID are required",
          status: 400,
        });
      }

      const newComplaint = await Complaint.create({
        userID,
        govID,
        complaint,
        category,
        status,
        lokasi,
        kecamatan,
        kabupaten,
        provinsi,
      });

      res.status(201).json({
        success: true,
        message: "Complaint created successfully",
        data: newComplaint,
        status: 201,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
        status: 500,
      });
    }
  },

  // Get all complaints
  getAllComplaints: async (req, res) => {
    try {
      const complaints = await Complaint.findAll();

      res.status(200).json({
        success: true,
        message: "Complaints retrieved successfully",
        data: complaints,
        status: 200,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
        status: 500,
      });
    }
  },

  // Get complaint by ID
  getComplaintById: async (req, res) => {
    try {
      const complaintId = req.params.complaintID; // Assume the ID is passed as a route parameter
      const complaint = await Complaint.findOne({
        where: { complaintID: complaintId },
      });

      if (!complaint) {
        return res.status(404).json({
          success: false,
          message: "Complaint not found",
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "Complaint retrieved successfully",
        data: complaint,
        status: 200,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
        status: 500,
      });
    }
  },

  // Get complaints by userID
  getComplaintsByUserId: async (req, res) => {
    try {
      const userId = req.params.userID; // Assume the userID is passed as a route parameter
      const complaints = await Complaint.findAll({
        where: { userID: userId },
      });

      if (!complaints || complaints.length === 0) {
        return res.status(404).json({
          success: false,
          message: "No complaints found for this user",
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "Complaints retrieved successfully",
        data: complaints,
        status: 200,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
        status: 500,
      });
    }
  },

  // Get complaints by govID
  getComplaintsByGovId: async (req, res) => {
    try {
      const govId = req.params.govID; // Assume the govID is passed as a route parameter
      const complaints = await Complaint.findAll({
        where: { govID: govId },
      });

      if (!complaints || complaints.length === 0) {
        return res.status(404).json({
          success: false,
          message: "No complaints found for this government",
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "Complaints retrieved successfully",
        data: complaints,
        status: 200,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
        status: 500,
      });
    }
  },

  // Update complaint by ID
  updateComplaint: async (req, res) => {
    try {
      const complaintId = req.params.complaintID; // Assume the ID is passed as a route parameter
      const updatedComplaintData = req.body;

      const [updated] = await Complaint.update(updatedComplaintData, {
        where: { complaintID: complaintId },
      });

      if (!updated) {
        return res.status(404).json({
          success: false,
          message: "Complaint not found",
          status: 404,
        });
      }

      const updatedComplaint = await Complaint.findOne({
        where: { complaintID: complaintId },
      });

      res.status(200).json({
        success: true,
        message: "Complaint updated successfully",
        data: updatedComplaint,
        status: 200,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
        status: 500,
      });
    }
  },

  // Delete complaint by ID
  deleteComplaint: async (req, res) => {
    try {
      const complaintId = req.params.complaintID; // Assume the ID is passed as a route parameter
      const deletedComplaint = await Complaint.destroy({
        where: { complaintID: complaintId },
      });

      if (!deletedComplaint) {
        return res.status(404).json({
          success: false,
          message: "Complaint not found",
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "Complaint deleted successfully",
        status: 200,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
        status: 500,
      });
    }
  },
};

module.exports = complaintController;