# Gedor
A mobile application designed to help government agencies streamline the management of public complaints. Users can easily submit complaints, monitor their progress, and receive updates, while the system ensures automated routing for efficient resolution.

## Overview
This capstone project is developed to fulfill the mandatory graduation requirements of the Bangkit program. 
We divide this project into 3 parts which are for the Machine Learning(ML), Cloud Computing(CC) and Mobile Development(MD) part. 

### Machine Learning

We use Tensorflow to build our model that will leverage machine learning to streamline the process of routing user complaints to the government, making their work more efficient. User complaints are automatically screened by our complaint detection model to ensure that inappropriate or irrelevant submissions are filtered out before reaching the government. Once filtered, the complaints are automatically categorized by our category detection model, ensuring they are accurately sorted based on their type. This approach simplifies the process for users by allowing them to submit complaints without worrying about classification, while also ensuring that only valid and well-categorized complaints are sent to the government, making it easier for them to review and address issues efficiently.

### Cloud Computing

We are developing an integration layer to seamlessly connect our database and machine learning model with our Android application. To build this, we use Cloud Run as our compute services that will act as the host and manage both of our API. For our database needs, we rely on CloudSQL, chosen for its user-friendly setup, robust features, and cost efficiency. This integration acts as a robust bridge, facilitating smooth and efficient data exchange while supporting real-time processing and analytics. As a result, it enhances the app's overall performance, scalability, and user experience, providing a seamless and dynamic solution.

### Mobile Development

For more detail of each part, you can visit each of the part folders that is described below:
- `ml` for Machine Learning part
- `cc` for Cloud Computing part
- `md` for Mobile Development part