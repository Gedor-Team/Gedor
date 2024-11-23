module.exports = (sequelize, DataTypes) => {
  const User = sequelize.define(
      'User',
      {
          userID: {
              type: DataTypes.INTEGER,
              autoIncrement: true,
              primaryKey: true,
              allowNull: false,
          },
          username: {
              type: DataTypes.STRING,
              allowNull: false,
              unique: true, // Ensure no duplicate usernames
              validate: {
                  len: [3, 50], // Username length between 3 and 50 characters
              },
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
              unique: true, // Ensure no duplicate emails
              validate: {
                  isEmail: true, // Check for valid email format
              },
          },
          phoneNumber: {
              type: DataTypes.STRING,
              allowNull: false,
              unique: true, // Ensure no duplicate phone numbers
              validate: {
                  isNumeric: true, // Check for numeric value
                  len: [10, 15], // Phone number length between 10 and 15 digits
              },
          },
      },
      {
          timestamps: true, // Automatically adds `createdAt` and `updatedAt`
          tableName: 'Users', // Explicit table name
      }
  );

  User.associate = (models) => {
      // One-to-Many relationship with Complaints
      User.hasMany(models.Complaint, {
          foreignKey: 'userID',
      });
  };

  return User;
};