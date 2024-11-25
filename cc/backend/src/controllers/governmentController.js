const Government= require('../models/governmentModel'); // Import Government model from Sequelize models
const bcrypt = require('bcrypt');

const governmentController = {
  // Add a new government
  addGovernment: async (req, res) => {
    try {
      const { username, password, name, address, phoneNumber, email } = req.body;

      // Validate required fields (basic example)
      if (!username || !password || !name || !phoneNumber || !email || !address) {
        return res.status(400).json({ success: false, message: "All fields are required" });
      }

      // Validate email with regex
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email validation regex
      if (!emailRegex.test(email)) {
        return res.status(400).json({ 
          success: false, 
          message: "Invalid email format" 
        });
      }

      // Validate phone number
      const phoneRegex = /^[0-9]{10,15}$/; // Accepts phone numbers with 10-15 digits
      if (!phoneRegex.test(phoneNumber)) {
        return res.status(400).json({ 
          success: false, 
          message: "Invalid phone number format. It should contain 10-15 digits." 
        });
      }

      // Generate salt and hash the password
      const saltRounds = 10; // Define the cost factor for bcrypt
      const salt = await bcrypt.genSalt(saltRounds);
      const hashedPassword = await bcrypt.hash(password, salt);

      const newGovernment = await Government.create({
        username,
        password: hashedPassword,
        salt,
        name,
        address,
        phoneNumber,
        email,
      });

      res.status(201).json({
        success: true,
        message: "Government created successfully",
        data: newGovernment,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Get all governments
  getAllGovernment: async (req, res) => {
    try {
      const governments = await Government.findAll(); // Sequelize's findAll method for retrieving all records
      res.status(200).json({
        success: true,
        data: governments,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Get government by ID
  getGovernmentById: async (req, res) => {
    try {
      const governmentId = req.params.govID; // Assume the ID is passed as a route parameter
      const government = await Government.findOne({ where: { govID: governmentId } }); // Sequelize's findOne with a condition

      if (!government) {
        return res
          .status(404)
          .json({ success: false, message: "Government not found" });
      }

      res.status(200).json({
        success: true,
        data: government,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Update government by ID
  updateGovernment: async (req, res) => {
    try {
      const governmentId = req.params.govID; // Assume the ID is passed as a route parameter
      const updatedGovernmentData = req.body;

      // Validate email if it's being updated
      if (updatedGovernmentData.email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(updatedUserData.email)) {
          return res.status(400).json({
            success: false,
            message: "Invalid email format",
          });
        }
      }

      // Validate phone number if it's being updated
      if (updatedGovernmentData.phoneNumber) {
        const phoneRegex = /^[0-9]{10,15}$/; // Accepts phone numbers with 10-15 digits
        if (!phoneRegex.test(updatedUserData.phoneNumber)) {
          return res.status(400).json({
            success: false,
            message: "Invalid phone number format. It should contain 10-15 digits.",
          });
        }
      }

      const [updated] = await Government.update(updatedGovernmentData, {
        where: { govID: governmentId },
      }); // Sequelize's update method

      if (!updated) {
        return res.status(404).json({ message: "Government not found" });
      }

      const updatedGovernment = await Government.findOne({ where: { govID: governmentId } }); // Fetch updated record
      res.status(200).json({
        success: true,
        message: "Government updated successfully",
        data: updatedGovernment,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Delete government by ID
  deleteGovernment: async (req, res) => {
    try {
      const governmentId = req.params.govID; // Assume the ID is passed as a route parameter
      const deletedGovernment = await Government.destroy({
        where: { govID: governmentId },
      }); // Sequelize's destroy method

      if (!deletedGovernment) {
        return res.status(404).json({ message: "Government not found" });
      }

      res.status(200).json({
        success: true,
        message: "Government deleted successfully",
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },
};

module.exports = governmentController;