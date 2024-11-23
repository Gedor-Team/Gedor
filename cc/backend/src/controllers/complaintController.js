const Complaint= require('../models/complaintModel'); // Import Complaint model from Sequelize models

const complaintController = {
  // Add a new complaint
  addComplaint: async (req, res) => {
    try {
      const { userID, govID, complaint, category, status, lokasi, kecamatan, kabupaten, provinsi } = req.body;

      // Make sure userID and govID are passed in the request
      if (!userID || !govID) {
        return res.status(400).json({ message: "userID and govID are required" });
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

      res.status(201).json(newComplaint);
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Get all complaints
  getAllComplaints: async (req, res) => {
    try {
      const complaints = await Complaint.findAll({
        // include: [
        //   {
        //     model: sequelize.models.User, // Include the User associated with each complaint
        //     as: 'user',
        //   },
        //   {
        //     model: sequelize.models.Government, // Include the Government associated with each complaint
        //     as: 'government',
        //   }
        // ],
      });
      res.status(200).json(complaints);
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Get complaint by ID
  getComplaintById: async (req, res) => {
    try {
      const complaintId = req.params.complaintID; // Assume the ID is passed as a route parameter
      const complaint = await Complaint.findOne({
        where: { complaintID: complaintId }
        // include: [
        //   {
        //     model: sequelize.models.User, // Include the User associated with the complaint
        //     as: 'user',
        //   },
        //   {
        //     model: sequelize.models.Government, // Include the Government associated with the complaint
        //     as: 'government',
        //   }
        // ],
      });

      if (!complaint) {
        return res
          .status(404)
          .json({ success: false, message: "Complaint not found" });
      }

      res.status(200).json({ success: true, data: complaint });
    } catch (error) {
      res.status(500).json({ success: false, message: error.message });
    }
  },

  // Get complaints by userID
  getComplaintsByUserId: async (req, res) => {
    try {
      const userId = req.params.userID; // Assume the userID is passed as a route parameter
      const complaints = await Complaint.findAll({
        where: { userID: userId }
        // include: [
        //   {
        //     model: sequelize.models.User, // Include the User associated with each complaint
        //     as: 'user',
        //   },
        //   {
        //     model: sequelize.models.Government, // Include the Government associated with each complaint
        //     as: 'government',
        //   }
        // ],
      });

      if (!complaints || complaints.length === 0) {
        return res.status(404).json({ message: "No complaints found for this user" });
      }

      res.status(200).json({ success: true, data: complaints });
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Get complaints by govID
  getComplaintsByGovId: async (req, res) => {
    try {
      const govId = req.params.govID; // Assume the govID is passed as a route parameter
      const complaints = await Complaint.findAll({
        where: { govID: govId }
        // include: [
        //   {
        //     model: sequelize.models.User, // Include the User associated with each complaint
        //     as: 'user',
        //   },
        //   {
        //     model: sequelize.models.Government, // Include the Government associated with each complaint
        //     as: 'government',
        //   }
        // ],
      });

      if (!complaints || complaints.length === 0) {
        return res.status(404).json({ message: "No complaints found for this government" });
      }

      res.status(200).json({ success: true, data: complaints });
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Update complaint by ID
  updateComplaint: async (req, res) => {
    try {
      const complaintId = req.params.complaintID; // Assume the ID is passed as a route parameter
      const updatedComplaintData = req.body;

      const [updated] = await Complaint.update(updatedComplaintData, {
        where: { complaintID: complaintId },
      }); // Sequelize's update method

      if (!updated) {
        return res.status(404).json({ message: "Complaint not found" });
      }

      const updatedComplaint = await Complaint.findOne({
        where: { complaintID: complaintId }
        // include: [
        //   {
        //     model: sequelize.models.User, // Include the User associated with the updated complaint
        //     as: 'user',
        //   },
        //   {
        //     model: sequelize.models.Government, // Include the Government associated with the updated complaint
        //     as: 'government',
        //   }
        // ],
      });

      res.json(updatedComplaint);
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Delete complaint by ID
  deleteComplaint: async (req, res) => {
    try {
      const complaintId = req.params.complaintID; // Assume the ID is passed as a route parameter
      const deletedComplaint = await Complaint.destroy({
        where: { complaintID: complaintId },
      }); // Sequelize's destroy method

      if (!deletedComplaint) {
        return res.status(404).json({ message: "Complaint not found" });
      }

      res.status(200).json({ message: "Complaint deleted successfully" });
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
    }
  },
};

module.exports = complaintController;
