const { Sequelize } = require("sequelize");
require("dotenv").config(); // Load environment variables from a .env file

// Initialize Sequelize with Public IP configuration
const sequelize = new Sequelize(
  process.env.DB_NAME, // Database name
  process.env.DB_USER, // Database username
  process.env.DB_PASS, // Database password
  {
    dialect: "mysql", // Specify MySQL as the dialect
    dialectOptions: {
      socketPath: process.env.INSTANCE_UNIX_SOCKET, // Use the Unix socket
    },
  },
);

// Export the Sequelize instance for use across the project
module.exports = sequelize;
