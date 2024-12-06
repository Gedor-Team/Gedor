const { DataTypes } = require("sequelize"); // Import Sequelize and DataTypes
const sequelize = require("../database/connection"); // Import your Sequelize connection

// Define the User model
const User = sequelize.define("User", {
  userID: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true,
    allowNull: false,
  },
  username: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true, // Ensure unique usernames
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  salt: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  email: {
    type: DataTypes.STRING,
    allowNull: true,
    unique: true, // Ensure unique emails
  },
  phoneNumber: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true, // Ensure unique phone numbers
  },
});

// Define associations
User.associate = (models) => {
  // Assuming there’s a 'Complaint' model, define the relationship
  User.hasMany(models.Complaint, {
    foreignKey: "userID",
    as: "complaints", // Defines the alias for the relationship
  });
};

// Sync the model with the database (in dev mode, using `force: true` will recreate tables)
(async () => {
  try {
    await sequelize.sync({ force: false }); // Use force: false for safe table creation in production
    console.log("User model synced successfully!");
  } catch (error) {
    console.error("Error syncing User model:", error);
  }
})();

// Export the User model
module.exports = User;
