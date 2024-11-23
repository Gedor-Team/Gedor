const express = require("express"); 

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

const PORT = 3000; 

app.listen(PORT, () => { 
    console.log(`API is listening on port ${PORT}`); 
});