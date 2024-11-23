const Government= require('../models/governmentModel'); // Import Government model from Sequelize models

const governmentController = {
  // Add a new government
  addGovernment: async (req, res) => {
    try {
      const { username, password, salt, name, address, phoneNumber, email } = req.body;

      const newGovernment = await Government.create({
        username,
        password,
        salt,
        name,
        address,
        phoneNumber,
        email,
      });

      res.status(201).json(newGovernment);
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Get all governments
  getAllGovernment: async (req, res) => {
    try {
      const governments = await Government.findAll(); // Sequelize's findAll method for retrieving all records
      res.status(200).json(governments);
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
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

      res.status(200).json({ success: true, data: government });
    } catch (error) {
      res.status(500).json({ success: false, message: error.message });
    }
  },

  // Update government by ID
  updateGovernment: async (req, res) => {
    try {
      const governmentId = req.params.govID; // Assume the ID is passed as a route parameter
      const updatedGovernmentData = req.body;

      const [updated] = await Government.update(updatedGovernmentData, {
        where: { govID: governmentId },
      }); // Sequelize's update method

      if (!updated) {
        return res.status(404).json({ message: "Government not found" });
      }

      const updatedGovernment = await Government.findOne({ where: { govID: governmentId } }); // Fetch updated record
      res.json(updatedGovernment);
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Server Error", error });
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

      res.status(200).json({ message: "Government deleted successfully" });
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
    }
  },
};

module.exports = governmentController;