const { Sequelize } = require('sequelize');
require('dotenv').config(); // Load environment variables from a .env file

// Initialize Sequelize with Public IP configuration
const sequelize = new Sequelize(
  process.env.DB_NAME,        // Database name
  process.env.DB_USER,        // Database username
  process.env.DB_PASSWORD,    // Database password
  {
    host: process.env.DB_HOST, // Public IP of your Cloud SQL instance
    dialect: 'mysql',          // Specify MySQL as the dialect
  }
);

// Export the Sequelize instance for use across the project
module.exports = sequelize;