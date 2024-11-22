// models/user.js
module.exports = (sequelize, DataTypes) => {
    const User = sequelize.define('User', {
      userID: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
        allowNull: false,
      },
      username: {
        type: DataTypes.STRING,
        allowNull: false,
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
      },
      phoneNumber: { 
        type: DataTypes.STRING,
        allowNull: false,
      },
    });
  
    return User;
  };