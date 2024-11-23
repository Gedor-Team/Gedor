const { DataTypes } = require('sequelize'); // Import Sequelize and DataTypes
const sequelize = require('../database/connection'); // Import your Sequelize connection

// Define the Complaint model
const Complaint = sequelize.define(
  'Complaint',
  {
    complaintID: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
      allowNull: false,
    },
    userID: {
      type: DataTypes.INTEGER,
      allowNull: true,
      references: {
        model: 'Users', // Reference the Users table
        key: 'userID',
      },
      onUpdate: 'CASCADE',
      onDelete: 'SET NULL',
    },
    govID: {
      type: DataTypes.INTEGER,
      allowNull: true,
      references: {
        model: 'Governments', // Reference the Governments table
        key: 'govID',
      },
      onUpdate: 'CASCADE',
      onDelete: 'SET NULL',
    },
    complaint: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    category: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    status: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    lokasi: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    kecamatan: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    kabupaten: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    provinsi: {
      type: DataTypes.STRING,
      allowNull: false,
    },
  },
  {
    timestamps: true, // Automatically includes `createdAt` and `updatedAt`
    tableName: 'Complaints', // Explicit table name
  }
);

// Define associations
Complaint.associate = (models) => {
  // Associate Complaint with User
  Complaint.belongsTo(models.User, {
    foreignKey: 'userID',
    as: 'user', // Optional alias
  });

  // Associate Complaint with Government
  Complaint.belongsTo(models.Government, {
    foreignKey: 'govID',
    as: 'government', // Optional alias
  });
};

// Export the Complaint model
module.exports = Complaint;