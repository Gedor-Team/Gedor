module.exports = (sequelize, DataTypes) => {
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
          allowNull: false,
          references: {
            model: 'Users', // Name of the target table (User model's table name)
            key: 'userID', // Key in the User table that this column references
          },
          onUpdate: 'CASCADE',
          onDelete: 'SET NULL',
        },
        govID: {
          type: DataTypes.INTEGER,
          allowNull: false,
          references: {
            model: 'Governments', // Name of the Government model's table
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
        timestamps: true,
        tableName: 'Complaints', // Optional: specify explicit table name
      }
    );
  
    // Define associations
    Complaint.associate = (models) => {
      // Associate Complaint with User
      Complaint.belongsTo(models.User, {
        foreignKey: 'userID',
      });
  
      // Associate Complaint with Government
      Complaint.belongsTo(models.Government, {
        foreignKey: 'govID',
      });
    };
  
    return Complaint;
  };
  