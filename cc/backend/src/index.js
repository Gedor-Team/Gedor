const express = require("express"); 
// const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require('./database/connection');
const app = express(); 

const userRouter = require("./routes/userRouter");
const complaintRouter = require("./routes/complaintRouter");
const governmentRouter = require("./routes/governmentRouter");

// For testing purposes 
app.get("/", (req, res) => { 
    res.send("<h1>GEDOR</h1>"); 
}); 

app.use(express.json());
app.use("/api/users", userRouter);
app.use("/api/complaints", complaintRouter);    
app.use("/api/govs",governmentRouter);

// Test if database connection is working
sequelize.authenticate()
    .then(() => console.log("Database connected successfully"))
    .catch((error) => console.error("Error connecting to database:", error));

const PORT = process.env.PORT || 8080; 

app.listen(PORT, () => { 
    console.log(`API is listening on port ${PORT}`); 
});