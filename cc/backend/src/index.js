const express = require("express"); 

const app = express(); 

const userRouter = require("./routes/userRouter");

// For testing purposes 
app.get("/", (req, res) => { 
    res.send("<h1>GEDOR</h1>"); 
}); 

app.use(express.json());
app.use("/api/users", userRouter);

const PORT = 3000; 

app.listen(PORT, () => { 
    console.log(`API is listening on port ${PORT}`); 
});