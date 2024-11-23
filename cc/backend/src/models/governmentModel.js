module.exports = (sequelize, DataTypes) => {
    const Government = sequelize.define('Government', {
      govID: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
        allowNull: false,
      },
      name: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      address: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      phoneNumber: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      email: {
        type: DataTypes.STRING,
        allowNull: false,
      }
    }, {
      timestamps: true,
      tablename: 'Governments',
    });
  
    // Define relationship with the Complaint model
    Government.associate = (models) => {
        Government.hasMany(models.Complaint, {
            foreignKey: 'govID',
        });
    };
  
    return Government;
};
  