const User = require('../models/userModel'); // Import User model from Sequelize models
const bcrypt = require('bcrypt');

const userController = {
  // Add a new user
  addUser: async (req, res) => {
    try {
      const { username, password, email, phoneNumber } = req.body;

      // Validate required fields (basic example)
      if (!username || !password || !phoneNumber) {
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

      const newUser = await User.create({
        username,
        password: hashedPassword,
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

  getUserByUsername: async (req, res) => {
    try {
      const user_name = req.params.username;
      const user = await User.findOne({ where: {username: user_name}});

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

      // Validate email if it's being updated
      if (updatedUserData.email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(updatedUserData.email)) {
          return res.status(400).json({
            success: false,
            message: "Invalid email format",
          });
        }
      }

      // Validate phone number if it's being updated
      if (updatedUserData.phoneNumber) {
        const phoneRegex = /^[0-9]{10,15}$/; // Accepts phone numbers with 10-15 digits
        if (!phoneRegex.test(updatedUserData.phoneNumber)) {
          return res.status(400).json({
            success: false,
            message: "Invalid phone number format. It should contain 10-15 digits.",
          });
        }
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
