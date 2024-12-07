const Government = require("../models/governmentModel"); // Import Government model from Sequelize models
const bcrypt = require("bcrypt");

const governmentController = {
  // Add a new government
  addGovernment: async (req, res) => {
    try {
      const { username, password, name, address, phoneNumber, email } = req.body;

      if (!username || !password || !name || !phoneNumber || !email || !address) {
        return res.status(400).json({
          success: false,
          status: 400,
          message: "All fields are required",
        });
      }

      // Validate username with regex
      const usernameRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,12}$/;
      if (!usernameRegex.test(username)) {
        return res.status(400).json({
          success: false,
          status: 400,
          message: "Invalid username format. It must be 8-12 characters long and include both letters and digits.",
        });
      }

      // Validate password with regex
      const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/;
      if (!passwordRegex.test(password)) {
        return res.status(400).json({
          success: false,
          status: 400,
          message: "Invalid password format. It must be 8-20 characters long and include both letters and digits.",
        });
      }

      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
        return res.status(400).json({
          success: false,
          status: 400,
          message: "Invalid email format",
        });
      }

      const phoneRegex = /^[0-9]{10,15}$/;
      if (!phoneRegex.test(phoneNumber)) {
        return res.status(400).json({
          success: false,
          status: 400,
          message: "Invalid phone number format. It should contain 10-15 digits.",
        });
      }

      const saltRounds = 10;
      const salt = await bcrypt.genSalt(saltRounds)
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
        status: 201,
        message: "Government created successfully",
        data: newGovernment,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        status: 500,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Get all governments
  getAllGovernment: async (req, res) => {
    try {
      const governments = await Government.findAll();
      res.status(200).json({
        success: true,
        status: 200,
        data: governments,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        status: 500,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Get government by ID
  getGovernmentById: async (req, res) => {
    try {
      const governmentId = req.params.govID;
      const government = await Government.findOne({ where: { govID: governmentId } });

      if (!government) {
        return res.status(404).json({
          success: false,
          status: 404,
          message: "Government not found",
        });
      }

      res.status(200).json({
        success: true,
        status: 200,
        data: government,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        status: 500,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Get government by username
  getGovernmentByUsername: async (req, res) => {
    try {
      const user_name = req.params.username;
      const government = await Government.findOne({ where: { username: user_name } });

      if (!government) {
        return res.status(404).json({
          success: false,
          status: 404,
          message: "Government not found",
        });
      }

      res.status(200).json({
        success: true,
        status: 200,
        data: government,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        status: 500,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Update government by ID
  updateGovernment: async (req, res) => {
    try {
      const governmentId = req.params.govID;
      const updatedGovernmentData = req.body;

      if (updatedGovernmentData.email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(updatedGovernmentData.email)) {
          return res.status(400).json({
            success: false,
            status: 400,
            message: "Invalid email format",
          });
        }
      }

      if (updatedGovernmentData.phoneNumber) {
        const phoneRegex = /^[0-9]{10,15}$/;
        if (!phoneRegex.test(updatedGovernmentData.phoneNumber)) {
          return res.status(400).json({
            success: false,
            status: 400,
            message: "Invalid phone number format. It should contain 10-15 digits.",
          });
        }
      }

      if (updatedGovernmentData.username) {
        // Validate username with regex
        const usernameRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,12}$/;
        if (!usernameRegex.test(username)) {
          return res.status(400).json({
            success: false,
            status: 400,
            message: "Invalid username format. It must be 8-12 characters long and include both letters and digits.",
          });
        }
      }

      if (updatedGovernmentData.password) {
        // Validate password with regex
        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/;
        if (!passwordRegex.test(password)) {
          return res.status(400).json({
            success: false,
            status: 400,
            message: "Invalid password format. It must be 8-20 characters long and include both letters and digits.",
          });
        }
      }

      const [updated] = await Government.update(updatedGovernmentData, { where: { govID: governmentId } });

      if (!updated) {
        return res.status(404).json({
          success: false,
          status: 404,
          message: "Government not found",
        });
      }

      const updatedGovernment = await Government.findOne({ where: { govID: governmentId } });
      res.status(200).json({
        success: true,
        status: 200,
        message: "Government updated successfully",
        data: updatedGovernment,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        status: 500,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },

  // Delete government by ID
  deleteGovernment: async (req, res) => {
    try {
      const governmentId = req.params.govID;
      const deletedGovernment = await Government.destroy({ where: { govID: governmentId } });

      if (!deletedGovernment) {
        return res.status(404).json({
          success: false,
          status: 404,
          message: "Government not found",
        });
      }

      res.status(200).json({
        success: true,
        status: 200,
        message: "Government deleted successfully",
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        success: false,
        status: 500,
        message: "Server Error",
        error: error.message || error,
      });
    }
  },
};

module.exports = governmentController;
