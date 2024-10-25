# weather-info-for-pincode
After importing the project onto your laptop, follow these steps:

Open a terminal and run the command mvn clean install to build the project.
Update Maven and refresh the project in your IDE.
Open the application.yaml file and add your database schema, username, password, OpenWeatherMap API key, and Google Cloud API key.
After setting up the project, run the Spring Boot application. Then, open Chrome or Postman and enter the following URI:
http://localhost:8082/api/weather/pincode/"123456"/date/"25-10-2024"
