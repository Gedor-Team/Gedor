const User = require("../models/userModel"); // Import User model from Sequelize models
const bcrypt = require("bcrypt");

const userController = {
  // Add a new users
  addUser: async (req, res) => {
    try {
      const { username, password, email, phoneNumber } = req.body;

      // Validate required fields (basic example)
      if (!username || !password || !phoneNumber) {
        return res.status(400).json({
          success: false,
          message: "All fields are required",
          status: 400,
        });
      }

      // Validate username with regexsss
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

      // Validate email with regex
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email validation regex
      if (!emailRegex.test(email)) {
        return res.status(400).json({
          success: false,
          message: "Invalid email format",
          status: 400,
        });
      }

      // Validate phone number
      const phoneRegex = /^[0-9]{10,15}$/; // Accepts phone numbers with 10-15 digits
      if (!phoneRegex.test(phoneNumber)) {
        return res.status(400).json({
          success: false,
          message: "Invalid phone number format. It should contain 10-15 digits.",
          status: 400,
        });
      }

      // Check if username already exists
      const existingUsername = await User.findOne({ where: { username } });
      if (existingUsername) {
        return res.status(409).json({
          success: false,
          message: "Username already exists.",
          status: 409,
        });
      }

      // Check if email already exists
      const existingEmail = await User.findOne({ where: { email } });
      if (existingEmail) {
        return res.status(409).json({
          success: false,
          message: "Email already exists.",
          status: 409,
        });
      }

      // Check if phone number already existss
      const existingPhoneNumber = await User.findOne({ where: { phoneNumber } });
      if (existingPhoneNumber) {
        return res.status(409).json({
          success: false,
          message: "Phone number already exists.",
          status: 409,
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

  // Get all users
  getAllUser: async (req, res) => {
    try {
      const users = await User.findAll(); // Sequelize's findAll method for retrieving all records

      if (!users.length) {
        return res.status(404).json({
          success: false,
          message: "No users found",
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "Users retrieved successfully",
        data: users,
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

  // Get user by ID -test
  getUserById: async (req, res) => {
    try {
      const userId = req.params.userID; // Assume the ID is passed as a route parameter
      const user = await User.findOne({ where: { userID: userId } }); // Sequelize's findOne with a condition

      if (!user) {
        return res.status(404).json({
          success: false,
          message: "User not found",
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "User retrieved successfully",
        data: user,
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

  // Get user by username
  getUserByUsername: async (req, res) => {
    try {
      const user_name = req.params.username;
      const user = await User.findOne({ where: { username: user_name } });

      if (!user) {
        return res.status(404).json({
          success: false,
          message: "User not found",
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "User retrieved successfully",
        data: user,
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

  // Update user by userID
  updateUser: async (req, res) => {
    try {
      const userId = req.params.userID; // Assume the ID is passed as a route parameter
      const updatedUserData = req.body;

      // Validate that there is data to update
      if (Object.keys(updatedUserData).length === 0) {
        return res.status(400).json({
          success: false,
          message: "No data provided to update",
          status: 400,
        });
      }

      // Validate email if it's being updated
      if (updatedUserData.email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(updatedUserData.email)) {
          return res.status(400).json({
            success: false,
            message: "Invalid email format",
            status: 400,
          });
        }

        // Check if email already exists
        const existingEmail = await User.findOne({ where: { email: updatedUserData.email } });
        if (existingEmail) {
          return res.status(409).json({
            success: false,
            message: "Email already exists.",
            status: 409,
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
            status: 400,
          });
        }

        // Check if phone number already exists
        const existingPhoneNumber = await User.findOne({ where: { phoneNumber: updatedUserData.phoneNumber } });
        if (existingPhoneNumber) {
          return res.status(409).json({
            success: false,
            message: "Phone number already exists.",
            status: 409,
          });
        }
      }

      if (updatedUserData.username) {
        // Validate username with regex
        const usernameRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,12}$/;
        if (!usernameRegex.test(updatedUserData.username)) {
          return res.status(400).json({
            success: false,
            status: 400,
            message: "Invalid username format. It must be 8-12 characters long and include both letters and digits.",
          });
        }

        // Check if username already exists
        const existingUsername = await User.findOne({ where: { username: updatedUserData.username } });
        if (existingUsername) {
          return res.status(409).json({
            success: false,
            message: "Username already exists.",
            status: 409,
          });
        }
      }

      if (updatedUserData.password) {
        // Validate password with regex
        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/;
        if (!passwordRegex.test(updatedUserData.password)) {
          return res.status(400).json({
            success: false,
            status: 400,
            message: "Invalid password format. It must be 8-20 characters long and include both letters and digits.",
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
          status: 404,
        });
      }

      const updatedUser = await User.findOne({ where: { userID: userId } }); // Fetch updated record
      res.status(200).json({
        success: true,
        message: "User updated successfully",
        data: updatedUser,
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
          status: 404,
        });
      }

      res.status(200).json({
        success: true,
        message: "User deleted successfully",
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

module.exports = userController;
