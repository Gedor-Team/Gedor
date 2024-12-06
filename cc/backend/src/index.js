const swaggerJsDoc = require("swagger-jsdoc");
const swaggerUi = require("swagger-ui-express");

const express = require("express");
// const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require("./database/connection");
const app = express();

const swaggerOptions = {
  definition: {
    openapi: "3.0.0",
    info: {
      title: "Gedor API",
      version: "1.0.0",
      description: "API documentation for the Gedor App",
    },
    servers: [
      {
        url: "http://localhost:8080",
        description: "Local Development Server",
      },
      {
        url: "https://gedor-api-service-47510555796.asia-southeast2.run.app/",
        description: "Production Server",
      },
    ],
    components: {
      schemas: {
        User: {
          type: "object",
          properties: {
            userID: {
              type: "integer",
              description: "The unique ID of the user",
            },
            username: {
              type: "string",
              description: "The username of the user",
            },
            password: {
              type: "string",
              description: "The password of the user",
            },
            salt: {
              type: "string",
              description: "The salt used for password encryption",
            },
            email: {
              type: "string",
              description: "The email address of the user",
            },
            phoneNumber: {
              type: "string",
              description: "The phone number of the user",
            },
          },
        },
        Government: {
          type: "object",
          properties: {
            govID: {
              type: "integer",
              description: "The unique ID of the government entity",
            },
            username: {
              type: "string",
              description: "The username of the government entity",
            },
            password: {
              type: "string",
              description: "The password of the government entity",
            },
            salt: {
              type: "string",
              description: "The salt used for password encryption",
            },
            name: {
              type: "string",
              description: "The name of the government entity",
            },
            address: {
              type: "string",
              description: "The address of the government entity",
            },
            phoneNumber: {
              type: "string",
              description: "The phone number of the government entity",
            },
            email: {
              type: "string",
              description: "The email address of the government entity",
            },
          },
        },
        Complaint: {
          type: "object",
          properties: {
            complaintID: {
              type: "integer",
              description: "The unique ID of the complaint",
            },
            userID: {
              type: "integer",
              description: "The user ID associated with the complaint",
            },
            govID: {
              type: "integer",
              description:
                "The government entity ID associated with the complaint",
            },
            complaint: {
              type: "string",
              description: "The complaint description",
            },
            category: {
              type: "string",
              description:
                'The category of the complaint (e.g., "sampah", "polusi")',
            },
            status: {
              type: "string",
              description:
                'The status of the complaint (e.g., "pending", "resolved")',
            },
            lokasi: {
              type: "string",
              description: "The location of the complaint",
            },
            kecamatan: {
              type: "string",
              description: "The sub-district of the complaint location",
            },
            kabupaten: {
              type: "string",
              description: "The district of the complaint location",
            },
            provinsi: {
              type: "string",
              description: "The province of the complaint location",
            },
          },
        },
      },
    },
    paths: {
      "/api/users": {
        get: {
          summary: "Get all users",
          tags: ["Users"],
          description: "Retrieve all users in the system",
          responses: {
            200: {
              description: "A list of users",
              content: {
                "application/json": {
                  schema: {
                    type: "array",
                    items: {
                      $ref: "#/components/schemas/User",
                    },
                  },
                },
              },
            },
          },
        },
        post: {
          summary: "Create a new user",
          tags: ["Users"],
          description: "Create a new user in the system",
          requestBody: {
            content: {
              "application/json": {
                schema: {
                  $ref: "#/components/schemas/User",
                },
              },
            },
          },
          responses: {
            201: {
              description: "User created successfully",
            },
            400: {
              description: "Bad Request",
            },
          },
        },
      },
      "/api/users/{userID}": {
        get: {
          summary: "Get user by ID",
          tags: ["Users"],
          description: "Retrieve a user by their ID",
          parameters: [
            {
              name: "userID",
              in: "path",
              required: true,
              schema: {
                type: "string",
              },
            },
          ],
          responses: {
            200: {
              description: "User data",
              content: {
                "application/json": {
                  schema: {
                    $ref: "#/components/schemas/User",
                  },
                },
              },
            },
            404: {
              description: "User not found",
            },
          },
        },
        patch: {
          summary: "Update user by ID",
          tags: ["Users"],
          description: "Update an existing user by their ID",
          parameters: [
            {
              name: "userID",
              in: "path",
              required: true,
              schema: {
                type: "string",
              },
            },
          ],
          requestBody: {
            content: {
              "application/json": {
                schema: {
                  $ref: "#/components/schemas/User",
                },
              },
            },
          },
          responses: {
            200: {
              description: "User updated successfully",
            },
            400: {
              description: "Bad Request",
            },
            404: {
              description: "User not found",
            },
          },
        },
        delete: {
          summary: "Delete user by ID",
          tags: ["Users"],
          description: "Delete a user by their ID",
          parameters: [
            {
              name: "userID",
              in: "path",
              required: true,
              schema: {
                type: "string",
              },
            },
          ],
          responses: {
            200: {
              description: "User deleted successfully",
            },
            404: {
              description: "User not found",
            },
          },
        },
      },
      "/api/users/login/{username}": {
        get: {
          summary: "Get user by username",
          tags: ["Users"],
          description: "Retrieve a user by their username",
          parameters: [
            {
              name: "username",
              in: "path",
              required: true,
              schema: {
                type: "string",
              },
            },
          ],
          responses: {
            200: {
              description: "User found",
              content: {
                "application/json": {
                  schema: {
                    $ref: "#/components/schemas/User",
                  },
                },
              },
            },
            404: {
              description: "User not found",
            },
          },
        },
      },
      "/api/govs": {
        get: {
          summary: "Get all governments",
          tags: ["Governments"],
          description: "Retrieve all government entities from the system",
          responses: {
            200: {
              description: "List of all governments",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      success: { type: "boolean" },
                      data: {
                        type: "array",
                        items: {
                          type: "object",
                          properties: {
                            govID: { type: "integer" },
                            username: { type: "string" },
                            name: { type: "string" },
                            address: { type: "string" },
                            phoneNumber: { type: "string" },
                            email: { type: "string" },
                          },
                        },
                      },
                    },
                  },
                },
              },
            },
            500: {
              description: "Server Error",
            },
          },
        },
        post: {
          summary: "Add a new government",
          tags: ["Governments"],
          description: "Creates a new government entity in the system",
          requestBody: {
            content: {
              "application/json": {
                schema: {
                  type: "object",
                  properties: {
                    username: { type: "string" },
                    password: { type: "string" },
                    name: { type: "string" },
                    address: { type: "string" },
                    phoneNumber: { type: "string" },
                    email: { type: "string" },
                  },
                  required: [
                    "username",
                    "password",
                    "name",
                    "address",
                    "phoneNumber",
                    "email",
                  ],
                },
              },
            },
          },
          responses: {
            201: {
              description: "Government created successfully",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      success: { type: "boolean" },
                      message: { type: "string" },
                      data: {
                        type: "object",
                        properties: {
                          govID: { type: "integer" },
                          username: { type: "string" },
                          name: { type: "string" },
                          address: { type: "string" },
                          phoneNumber: { type: "string" },
                          email: { type: "string" },
                        },
                      },
                    },
                  },
                },
              },
            },
            400: {
              description: "Bad Request",
            },
            500: {
              description: "Server Error",
            },
          },
        },
      },

      "/api/govs/{govID}": {
        get: {
          summary: "Get government by ID",
          tags: ["Governments"],
          description: "Retrieve a government entity by its ID",
          parameters: [
            {
              name: "govID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          responses: {
            200: {
              description: "Government found",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      success: { type: "boolean" },
                      data: {
                        type: "object",
                        properties: {
                          govID: { type: "integer" },
                          username: { type: "string" },
                          name: { type: "string" },
                          address: { type: "string" },
                          phoneNumber: { type: "string" },
                          email: { type: "string" },
                        },
                      },
                    },
                  },
                },
              },
            },
            404: {
              description: "Government not found",
            },
            500: {
              description: "Server Error",
            },
          },
        },
        patch: {
          summary: "Update government by ID",
          tags: ["Governments"],
          description: "Update the details of a government entity by its ID",
          parameters: [
            {
              name: "govID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          requestBody: {
            content: {
              "application/json": {
                schema: {
                  type: "object",
                  properties: {
                    username: { type: "string" },
                    name: { type: "string" },
                    address: { type: "string" },
                    phoneNumber: { type: "string" },
                    email: { type: "string" },
                  },
                },
              },
            },
          },
          responses: {
            200: {
              description: "Government updated successfully",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      success: { type: "boolean" },
                      message: { type: "string" },
                      data: {
                        type: "object",
                        properties: {
                          govID: { type: "integer" },
                          username: { type: "string" },
                          name: { type: "string" },
                          address: { type: "string" },
                          phoneNumber: { type: "string" },
                          email: { type: "string" },
                        },
                      },
                    },
                  },
                },
              },
            },
            400: {
              description: "Invalid input",
            },
            404: {
              description: "Government not found",
            },
            500: {
              description: "Server Error",
            },
          },
        },
        delete: {
          summary: "Delete government by ID",
          tags: ["Governments"],
          description: "Delete a government entity by its ID",
          parameters: [
            {
              name: "govID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          responses: {
            200: {
              description: "Government deleted successfully",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      success: { type: "boolean" },
                      message: { type: "string" },
                    },
                  },
                },
              },
            },
            404: {
              description: "Government not found",
            },
            500: {
              description: "Server Error",
            },
          },
        },
      },

      "/api/govs/login/{username}": {
        get: {
          summary: "Get government by username",
          tags: ["Governments"],
          description: "Retrieve a government entity by its username",
          parameters: [
            {
              name: "username",
              in: "path",
              required: true,
              schema: {
                type: "string",
              },
            },
          ],
          responses: {
            200: {
              description: "Government found",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      success: { type: "boolean" },
                      data: {
                        type: "object",
                        properties: {
                          govID: { type: "integer" },
                          username: { type: "string" },
                          name: { type: "string" },
                          address: { type: "string" },
                          phoneNumber: { type: "string" },
                          email: { type: "string" },
                        },
                      },
                    },
                  },
                },
              },
            },
            404: {
              description: "Government not found",
            },
            500: {
              description: "Server Error",
            },
          },
        },
      },
      "/api/complaints": {
        post: {
          summary: "Add a new complaint",
          tags: ["Complaints"],
          description: "Creates a new complaint in the system",
          requestBody: {
            content: {
              "application/json": {
                schema: {
                  type: "object",
                  properties: {
                    userID: { type: "integer" },
                    govID: { type: "integer" },
                    complaint: { type: "string" },
                    category: { type: "string" },
                    status: { type: "string" },
                    lokasi: { type: "string" },
                    kecamatan: { type: "string" },
                    kabupaten: { type: "string" },
                    provinsi: { type: "string" },
                  },
                  required: ["userID", "govID", "complaint", "status"],
                },
              },
            },
          },
          responses: {
            201: {
              description: "Complaint added successfully",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      complaintID: { type: "integer" },
                      userID: { type: "integer" },
                      govID: { type: "integer" },
                      complaint: { type: "string" },
                      category: { type: "string" },
                      status: { type: "string" },
                      lokasi: { type: "string" },
                      kecamatan: { type: "string" },
                      kabupaten: { type: "string" },
                      provinsi: { type: "string" },
                    },
                  },
                },
              },
            },
            400: {
              description: "Invalid input",
            },
            500: {
              description: "Server Error",
            },
          },
        },
        get: {
          summary: "Get all complaints",
          tags: ["Complaints"],
          description: "Retrieve all complaints",
          responses: {
            200: {
              description: "List of all complaints",
              content: {
                "application/json": {
                  schema: {
                    type: "array",
                    items: {
                      type: "object",
                      properties: {
                        complaintID: { type: "integer" },
                        userID: { type: "integer" },
                        govID: { type: "integer" },
                        complaint: { type: "string" },
                        category: { type: "string" },
                        status: { type: "string" },
                        lokasi: { type: "string" },
                        kecamatan: { type: "string" },
                        kabupaten: { type: "string" },
                        provinsi: { type: "string" },
                      },
                    },
                  },
                },
              },
            },
            500: {
              description: "Server Error",
            },
          },
        },
      },
      "/api/complaints/{complaintID}": {
        get: {
          summary: "Get complaint by ID",
          tags: ["Complaints"],
          description: "Retrieve a complaint by its ID",
          parameters: [
            {
              name: "complaintID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          responses: {
            200: {
              description: "Complaint found",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      complaintID: { type: "integer" },
                      userID: { type: "integer" },
                      govID: { type: "integer" },
                      complaint: { type: "string" },
                      category: { type: "string" },
                      status: { type: "string" },
                      lokasi: { type: "string" },
                      kecamatan: { type: "string" },
                      kabupaten: { type: "string" },
                      provinsi: { type: "string" },
                    },
                  },
                },
              },
            },
            404: {
              description: "Complaint not found",
            },
            500: {
              description: "Server Error",
            },
          },
        },
        patch: {
          summary: "Update complaint by ID",
          tags: ["Complaints"],
          description: "Update the details of a complaint by its ID",
          parameters: [
            {
              name: "complaintID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          requestBody: {
            content: {
              "application/json": {
                schema: {
                  type: "object",
                  properties: {
                    complaint: { type: "string" },
                    category: { type: "string" },
                    status: { type: "string" },
                    lokasi: { type: "string" },
                    kecamatan: { type: "string" },
                    kabupaten: { type: "string" },
                    provinsi: { type: "string" },
                  },
                },
              },
            },
          },
          responses: {
            200: {
              description: "Complaint updated successfully",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      complaintID: { type: "integer" },
                      userID: { type: "integer" },
                      govID: { type: "integer" },
                      complaint: { type: "string" },
                      category: { type: "string" },
                      status: { type: "string" },
                      lokasi: { type: "string" },
                      kecamatan: { type: "string" },
                      kabupaten: { type: "string" },
                      provinsi: { type: "string" },
                    },
                  },
                },
              },
            },
            400: {
              description: "Invalid input",
            },
            404: {
              description: "Complaint not found",
            },
            500: {
              description: "Server Error",
            },
          },
        },
        delete: {
          summary: "Delete complaint by ID",
          tags: ["Complaints"],
          description: "Delete a complaint by its ID",
          parameters: [
            {
              name: "complaintID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          responses: {
            200: {
              description: "Complaint deleted successfully",
              content: {
                "application/json": {
                  schema: {
                    type: "object",
                    properties: {
                      message: { type: "string" },
                    },
                  },
                },
              },
            },
            404: {
              description: "Complaint not found",
            },
            500: {
              description: "Server Error",
            },
          },
        },
      },
      "/api/complaints/users/{userID}": {
        get: {
          summary: "Get complaints by user ID",
          tags: ["Complaints"],
          description: "Retrieve all complaints for a specific user",
          parameters: [
            {
              name: "userID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          responses: {
            200: {
              description: "Complaints found for this user",
              content: {
                "application/json": {
                  schema: {
                    type: "array",
                    items: {
                      type: "object",
                      properties: {
                        complaintID: { type: "integer" },
                        userID: { type: "integer" },
                        govID: { type: "integer" },
                        complaint: { type: "string" },
                        category: { type: "string" },
                        status: { type: "string" },
                        lokasi: { type: "string" },
                        kecamatan: { type: "string" },
                        kabupaten: { type: "string" },
                        provinsi: { type: "string" },
                      },
                    },
                  },
                },
              },
            },
            404: {
              description: "No complaints found for this user",
            },
            500: {
              description: "Server Error",
            },
          },
        },
      },
      "/api/complaints/govs/{govID}": {
        get: {
          summary: "Get complaints by government ID",
          tags: ["Complaints"],
          description: "Retrieve all complaints for a specific government",
          parameters: [
            {
              name: "govID",
              in: "path",
              required: true,
              schema: {
                type: "integer",
              },
            },
          ],
          responses: {
            200: {
              description: "Complaints found for this government",
              content: {
                "application/json": {
                  schema: {
                    type: "array",
                    items: {
                      type: "object",
                      properties: {
                        complaintID: { type: "integer" },
                        userID: { type: "integer" },
                        govID: { type: "integer" },
                        complaint: { type: "string" },
                        category: { type: "string" },
                        status: { type: "string" },
                        lokasi: { type: "string" },
                        kecamatan: { type: "string" },
                        kabupaten: { type: "string" },
                        provinsi: { type: "string" },
                      },
                    },
                  },
                },
              },
            },
            404: {
              description: "No complaints found for this government",
            },
            500: {
              description: "Server Error",
            },
          },
        },
      },
    },
  },
  apis: ["./routes/*.js"], // Add your route files here
};

const swaggerDocs = swaggerJsDoc(swaggerOptions);

const userRouter = require("./routes/userRouter");
const complaintRouter = require("./routes/complaintRouter");
const governmentRouter = require("./routes/governmentRouter");

// For testing purposes
app.get("/", (req, res) => {
  res.send("<h1>GEDOR</h1>");
});

app.use(express.json());
app.use("/api/docs", swaggerUi.serve, swaggerUi.setup(swaggerDocs));
app.use("/api/users", userRouter);
app.use("/api/complaints", complaintRouter);
app.use("/api/govs", governmentRouter);

// Test if database connection is working
sequelize
  .authenticate()
  .then(() => console.log("Database connected successfully"))
  .catch((error) => console.error("Error connecting to database:", error));

const PORT = process.env.PORT || 8080;

app.listen(PORT, () => {
  console.log(`API is listening on port ${PORT}`);
});
