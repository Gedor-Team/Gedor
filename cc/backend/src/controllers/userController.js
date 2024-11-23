const User = require('../models/userModel'); // Import User model from Sequelize models

const userController = {
  // Add a new user
  addUser: async (req, res) => {
    try {
      const { username, password, salt, email, phoneNumber } = req.body;

      // Validate required fields (basic example)
      if (!username || !password || !salt || !email || !phoneNumber) {
        return res.status(400).json({ success: false, message: "All fields are required" });
      }

      const newUser = await User.create({
        username,
        password,
        salt,
        email,
        phoneNumber,
      });

      res.status(201).json({
        success: true,
        message: "User created successfully",
        data: newUser,
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

  // Get all users
  getAllUser: async (req, res) => {
    try {
      const users = await User.findAll(); // Sequelize's findAll method for retrieving all records

      if (!users.length) {
        return res.status(404).json({ success: false, message: "No users found" });
      }

      res.status(200).json({
        success: true,
        data: users,
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

  // Get user by ID
  getUserById: async (req, res) => {
    try {
      const userId = req.params.userID; // Assume the ID is passed as a route parameter
      const user = await User.findOne({ where: { userID: userId } }); // Sequelize's findOne with a condition

      if (!user) {
        return res.status(404).json({
          success: false,
          message: "User not found",
        });
      }

      res.status(200).json({
        success: true,
        data: user,
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

  // Update user by ID
  updateUser: async (req, res) => {
    try {
      const userId = req.params.userID; // Assume the ID is passed as a route parameter
      const updatedUserData = req.body;

      // Validate that there is data to update
      if (Object.keys(updatedUserData).length === 0) {
        return res.status(400).json({
          success: false,
          message: "No data provided to update",
        });
      }

      const [updated] = await User.update(updatedUserData, {
        where: { userID: userId },
      }); // Sequelize's update method

      if (!updated) {
        return res.status(404).json({
          success: false,
          message: "User not found",
        });
      }

      const updatedUser = await User.findOne({ where: { userID: userId } }); // Fetch updated record
      res.status(200).json({
        success: true,
        message: "User updated successfully",
        data: updatedUser,
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

  // Delete user by ID
  deleteUser: async (req, res) => {
    try {
      const userId = req.params.userID; // Assume the ID is passed as a route parameter
      const deletedUser = await User.destroy({
        where: { userID: userId },
      }); // Sequelize's destroy method

      if (!deletedUser) {
        return res.status(404).json({
          success: false,
          message: "User not found",
        });
      }

      res.status(200).json({
        success: true,
        message: "User deleted successfully",
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

module.exports = userController;
