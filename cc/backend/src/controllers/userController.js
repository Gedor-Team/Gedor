const { User } = require('../models/userModel'); // Import User model from Sequelize models

const userController = {
  // Add a new user
  addUser: async (req, res) => {
    try {
      const { username, password, salt, email, phoneNumber } = req.body;

      const newUser = await User.create({
        username,
        password,
        salt,
        email,
        phoneNumber,
      });

      res.status(201).json(newUser);
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Get all users
  getAllUser: async (req, res) => {
    try {
      const users = await User.findAll(); // Sequelize's findAll method for retrieving all records
      res.status(200).json(users);
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Get user by ID
  getUserById: async (req, res) => {
    try {
      const userId = req.params.id; // Assume the ID is passed as a route parameter
      const user = await User.findOne({ where: { userID: userId } }); // Sequelize's findOne with a condition

      if (!user) {
        return res
          .status(404)
          .json({ success: false, message: "User not found" });
      }

      res.status(200).json({ success: true, data: user });
    } catch (error) {
      res.status(500).json({ success: false, message: error.message });
    }
  },

  // Update user by ID
  updateUser: async (req, res) => {
    try {
      const userId = req.params.id; // Assume the ID is passed as a route parameter
      const updatedUserData = req.body;

      const [updated] = await User.update(updatedUserData, {
        where: { userID: userId },
      }); // Sequelize's update method

      if (!updated) {
        return res.status(404).json({ message: "User not found" });
      }

      const updatedUser = await User.findOne({ where: { userID: userId } }); // Fetch updated record
      res.json(updatedUser);
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Server Error", error });
    }
  },

  // Delete user by ID
  deleteUser: async (req, res) => {
    try {
      const userId = req.params.id; // Assume the ID is passed as a route parameter
      const deletedUser = await User.destroy({
        where: { userID: userId },
      }); // Sequelize's destroy method

      if (!deletedUser) {
        return res.status(404).json({ message: "User not found" });
      }

      res.status(200).json({ message: "User deleted successfully" });
    } catch (error) {
      res.status(500).json({ message: "Server Error", error });
    }
  },
};

module.exports = userController;