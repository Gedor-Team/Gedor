const { DataTypes } = require("sequelize"); // Import Sequelize and DataTypes
const sequelize = require("../database/connection"); // Import your Sequelize connection

const Government = sequelize.define(
  "Government",
  {
    govID: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
      allowNull: false,
    },
    username: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
    password: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    salt: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    name: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
    address: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
    phoneNumber: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
  },
  {
    timestamps: true,
    tablename: "Governments",
  },
);

// Define relationship with the Complaint model
Government.associate = (models) => {
  Government.hasMany(models.Complaint, {
    foreignKey: "govID",
  });
};

// Sync the model with the database (in dev mode, using `force: true` will recreate tables)
(async () => {
  try {
    await sequelize.sync({ force: false }); // Use force: false for safe table creation in production
    console.log("Government model synced successfully!");
  } catch (error) {
    console.error("Error syncing Government model:", error);
  }
})();

// Export the User model
module.exports = Government;
